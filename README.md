# Pimp my Foreach Loop

Original blog post, <http://www.iam.unibe.ch/~akuhn/blog/2008/pimp-my-foreach-loop>

Java’s foreach loop is useful but limited. Neither can we access the iterator of a running loop, nor can we write higher-order foreach loops, as they are common in Smalltalk, Javascript, or Ruby

    names = words.select { |each| each.frist.upcase? }     

    bool = words.any? { |each| each.size > 7 }

    length = words.inject(0) { |sum,each| sum + each.size }

We all know, this is because Java is – even though in its 7th release – still without the most basic structure of a programming language: Clo-clo-closures! 

There are workarounds. Typically, anonymous inner-classes are (ab)used as closures. I don't like that. All the syntactic clutter and implementation overhead of using full-blown classes, only to inject some simple behavior into a loop? IMHO, not worth it.

Instead, here is a small DSL that pimps ye old Java foreach loop for you:

	for (Select<String> each: ForEach.select(words)) {
	  each.yield = each.value.charAt(0).isUppercase();
	}
	Collection<String> names = ForEach.result();
	for (AnySatisfy<String> each: ForEach.anySatisfy(words)) {
	  each.yield = each.value.length() > 7;
	}
	boolean bool = ForEach.result();
	for (Inject<Integer,String> each: ForEach.inject(0,words)) {
	  each.yield += each.value.length();
	}
	int length = ForEach.result();

How does it work?

Behind the scenes, Java’s foreach loop operates on an instance of Iterable. Thus we can hook a custom object into the loop and get called back upon starting and terminating the loop, as well as before and after each iteration.

In the first example, #select is a static method that wraps the given collection in a custom object. The custom object is of type Iterable&lt;Select&gt;, where Select has two fields. One field is used a input parameter, the other as output parameter. Before each iteration of the loop, value is populated with the current element. After each iteration, yield is polled for a boolean value. Inbetween, the loop is executed.

While running the loop, all elements for which the loop yields true are copied into a new collection. Upon terminating the loop, this collection is assigned to $result. To keep things thread-safe, the result is stored in a ThreadLocal variable .

The same technique is used in the two other examples. #anySatisfy checks if all iterations of the loop yield true. #inject combines all elements by injecting an accumulator value into each iteration of the loop.

The list of currently supported queries includes

- [All](blob/master/ForEach/src/ch/akuhn/foreach/All.java)
- [Any](blob/master/ForEach/src/ch/akuhn/foreach/Any.java)
- [Collect](blob/master/ForEach/src/ch/akuhn/foreach/Collect.java)
- [Count](blob/master/ForEach/src/ch/akuhn/foreach/Count.java)
- [CutPieces](blob/master/ForEach/src/ch/akuhn/foreach/CutPieces.java)
- [Detect](blob/master/ForEach/src/ch/akuhn/foreach/Detect.java)
- [Fold](blob/master/ForEach/src/ch/akuhn/foreach/Fold.java)
- [GroupedBy](blob/master/ForEach/src/ch/akuhn/foreach/GroupedBy.java)
- [IndexOf](blob/master/ForEach/src/ch/akuhn/foreach/IndexOf.java)
- [Inject](blob/master/ForEach/src/ch/akuhn/foreach/Inject.java)
- [Reject](blob/master/ForEach/src/ch/akuhn/foreach/Reject.java)
- [Select](blob/master/ForEach/src/ch/akuhn/foreach/Select.java)

If you need more, you can subclass For<Each> and implement your own query. As an example, I shall leave the implementation of Count here:

	public class Count<Each> extends For<Each> {
	  public Each value;
	  public boolean yield;
	  private int count;
	  protected void afterEach() {
	    if (yield) count++;
	  }
	  protected Object afterLoop() {
	    return count;
	  }
	  protected void beforeLoop() {
	    count = 0;
	  }
	  protected void beforeEach(Each element) {
	    value = element;
	    yield = false;
	  }
	}

Have fun!