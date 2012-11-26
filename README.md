# Pimp my Foreach Loop

Original blog post, <http://www.iam.unibe.ch/~akuhn/blog/2008/pimp-my-foreach-loop>

Java’s foreach loop is useful but limited. Neither can we access the iterator of a running loop, nor can we write higher-order foreach loops, as they are common in Smalltalk, Javascript, or Ruby. We all know, this is because Java is – even though in its 7th release – still without the most basic structure of a programming language: Clo-clo-closures! 

There are workarounds. Typically, anonymous inner-classes are (ab)used as closures. I don't like that. All the syntactic clutter and implementation overhead of using full-blown classes, only to inject some simple behavior into a loop? IMHO, not worth it.

Instead, here is a small DSL that pimps ye old Java foreach loop for you:

Given a collection of words,

	Collection<String> words = // read words from a file ...

you may select all proper names,

	for (Select<String> each: ForEach.select(words)) {
	  each.yield = each.value.charAt(0).isUppercase();
	}
	Collection<String> names = ForEach.result();

or group words by length,
	
	for (GroupBy<String> each: ForEach.groupBy(words)) {
	  each.yield = each.value.length();
	}
	Map<Integer,String> groups = ForEach.result();
	
or sum up total length of all words,
	
	for (Sum<String> each: ForEach.sum(0,words)) {
	  each.yield += each.value.length();
	}
	int length = ForEach.result();

How does it work?

Behind the scenes, Java’s foreach loop operates on an instance of Iterable. Thus we can hook a custom object into the loop and get called back upon starting and terminating the loop, as well as before and after each iteration.

In the first example, `#select` is a static method that wraps the given collection in a custom object. The custom object is of type `Iterable&lt;Select&gt;`, where `Select` has two fields. One field is used a input parameter, the other as output parameter. Before each iteration of the loop, value is populated with the current element. After each iteration, yield is polled for a boolean value. In between, the loop is executed.

While running the loop, all elements for which the loop yields true are copied into a new collection. Upon terminating the loop, this collection is assigned to `ForEach#result`. To keep things thread-safe, the result is stored in a ThreadLocal variable .

The same technique is used in the two other examples. `#groupBy` groups elements by a key return from each iteration of the loop. `#sum` combines all elements by injecting an accumulator value into each iteration of the loop.

The list of currently supported queries includes

- [All](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/All.java) returns true if all elements match a given predicate.
- [Any](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Any.java) return true if at least one element matches a given predicate.
- [Collect](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Collect.java) applies a given expression to all elements and collects the results.
- [Count](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Count.java) counts how many elements match a given predicate. 
- [CutPieces](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/CutPieces.java) splits a collection based on a given predicate.
- [Detect](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Detect.java) finds the first element that matches a given predicate.
- [Fold](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Fold.java) returns the result of applying a given expression on each element and the intermediate result, starting with the first two elements. 
- [GroupBy](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/GroupBy.java) groups element by the result of applying an expression.
- [IndexOf](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/IndexOf.java) returns the index of the first element that matches a given predicate.
- [Inject](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Inject.java) returns the result of applying a given expression on each element and the intermediate result, starting with a seed value and the first elements. 
- [Reject](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Reject.java) returns all elements that are *not* matching a given predicate.
- [Select](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Select.java) returns all elements that match a given predicate. 
- [Sum](https://github.com/akuhn/foreach/blob/master/ForEach/src/ch/akuhn/foreach/Sum.java) same as `Inject` but accumulating the result into a numeric value.

If you need more, subclass `For<Each>` and implement your own query. As an example, I shall leave the implementation of `Count` here:

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