package com.laboon;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.*;

/**
 * Test methods in ProgramStack class
 */

public class ProgramStackTest {

    // Test that pushing a positive integer (20) will result in the next
    // pop being that value.
    @Test
    public void testPositivePush() {
	ProgramStack stack = new ProgramStack();
	final int testVal = 20;
	stack.push(testVal);
	int topVal = stack.pop();
	assertEquals(testVal, topVal);
    }
	
    // Test that pushing a negative integer (-1) will result in the next
    // pop being that value.
    @Test
    public void testNegativePush() {
	ProgramStack stack = new ProgramStack();
	final int testVal = -1;
	stack.push(testVal);
	int topVal = stack.pop();
	assertEquals(testVal, topVal);
    }

    // Test that pushing a zero (0) will result in the next
    // pop being that value.
    @Test
    public void testZeroPush() {
	ProgramStack stack = new ProgramStack();
	final int testVal = 0;
	stack.push(testVal);
	int topVal = stack.pop();
	assertEquals(testVal, topVal);
    }

    // Popping a newly-created stack (with 0 elements) should return
    // 0 (and not an EmptyStackException like a normal Java stack would)
    @Test
    public void testPop0Elements() {
	ProgramStack stack = new ProgramStack();
	try {
	    int val = stack.pop();
	    assertEquals(val, 0);
	} catch (EmptyStackException esex) {
	    fail();
	}
    }

    // Peeking a newly-created stack (with 0 elements) should return
    // 0 (and not an EmptyStackException like a normal Java stack would)
    @Test
    public void testPeek0Elements() {
	ProgramStack stack = new ProgramStack();
	try {
	    int val = stack.peek();
	    assertEquals(val, 0);
	} catch (EmptyStackException esex) {
	    fail();
	}

    }

    // Popping an element with multiple elements should return the
    // top element only.
    @Test
    public void testPopWithManyElements() {
	ProgramStack stack = new ProgramStack();
	for (int j = 0; j < 10; j++) {
	    stack.push(j);
	}
	assertEquals(stack.pop(), 9);
	assertEquals(stack.pop(), 8);
	assertEquals(stack.pop(), 7);
    }
    
    // Peeking an element with multiple elements should return the
    // top element only.  The stack should remain the same, so
    // multiple peeks should return the same value.
    @Test
    public void testPeekWithManyElements() {
	ProgramStack stack = new ProgramStack();
	for (int j = 0; j < 10; j++) {
	    stack.push(j);
	}
	assertEquals(stack.peek(), 9);
    }

    // Pushing and popping multiple elements should not cause any problems.
    @Test
    public void testPushPopMultiple() {
	ProgramStack stack = new ProgramStack();
	int retVal = 0;
	for (int j = 0; j < 10; j++) {
	    stack.push(j);
	    assertEquals(stack.peek(), j);
	}
	for (int j = 0; j < 10; j++) {
	    assertEquals(stack.pop(), (9 - j));
	}
	assertEquals(stack.peek(), 0);
	
    }

    // An empty ProgramStack should be represented by the String "[]".
    @Test
    public void testToStringEmpty() {
	ProgramStack stack = new ProgramStack();
	assertTrue(stack.toString().equals("[]"));
    }
    
    // A ProgramStack with one element should be represented by that one
    // element surrounded by brackets, e.g. "[8]".
    @Test
    public void testToStringOne() {
	ProgramStack stack = new ProgramStack();
	stack.push(8);
	assertTrue(stack.toString().equals("[8]"));

    }
    
    // A ProgramStack with multiple elements should consist of the elements
    // in that Stack in order, separated by commas and spaces.
    @Test
    public void testToStringMultiple() {
	ProgramStack stack = new ProgramStack();
	stack.push(1);
	stack.push(2);
	stack.push(3);
	assertTrue(stack.toString().equals("[1, 2, 3]"));
    }
    
	// Tests that toString returns [] with no stack
	@Test
	public void testToStringNull() {
		ProgramStack stack = new ProgramStack();
		assertEquals("[]", stack.toString());
	}
	
	// Tests that toString returns [L] with only L on the stack
	@Test
	public void testToStringL() {
		ProgramStack stack = new ProgramStack();
		stack.push('L');
		assertEquals("[76]", stack.toString());
	}
	
	// Test that toString returns each element in the stack separated by commas
	//String used is LABOON
	@Test
	public void testToStringLABOON() {
		ProgramStack stack = new ProgramStack();
		stack.push('L');
		stack.push('A');
		stack.push('B');
		stack.push('O');
		stack.push('O');
		stack.push('N');
		assertEquals("[76, 65, 66, 79, 79, 78]", stack.toString());
	}
}
