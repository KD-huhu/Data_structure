package _03_栈_队列;

import java.util.Stack;

import javax.xml.soap.Node;

/**
 * https://leetcode-cn.com/problems/min-stack/
 * @author DELL
 * 思路1：使用两个栈，一个是正常的栈，另一个栈中存放当前栈中的最小值
 * 		当正常栈push操作时将最小栈的栈顶和push的元素比较：
 * 		push数据较小则push自己
 * 		反之将最小栈顶部元素再次入栈
 * 		正常栈pop时，两个栈同时pop
 * 思路2：当push元素时需要知道当前栈内的最小值
 * 		使用链表实现
 * 		链表中的节点存储两部分数据
 * 		一部分是当前栈顶元素
 * 		另一部分是当前最小值
 */
@SuppressWarnings("unused")
public class _155_最小栈 {
/*
	// 思路1
	// 用来存放正常数据 
	private Stack<Integer> stack;
	// 用来存放最小数据 
	private Stack<Integer> minStack;

    // initialize your data structure here. 
//    public MinStack() {
//    	stack = new Stack<>();
//    	minStack = new Stack<>();
//    }
    
    public void push(int x) {
    	stack.push(x);
    	if (minStack.isEmpty()) {
    		minStack.push(x);
    	} else {
    		minStack.push(Math.min(x, minStack.peek()));
    	}
    }
    
    public void pop() {
    	stack.pop();
    	minStack.pop();
    }
    
    public int top() {
    	return stack.peek();
    }
    
    public int getMin() {
    	return minStack.peek();
    }
  */
	
	// 思路2
	private Node head;
	// initialize your data structure here.
//    public MinStack() {
//    	head = new Node(0, Integer.MAX_VALUE, null);
//    }
    
    public void push(int x) {
    	head = new Node(x, Math.min(x, head.min), head);
    }
    
    public void pop() {
		head =head.next;
	}
    
    public int top() {
		return head.val;
	}
    
    public int getMin() {
    	return head.min;
    }
    
    private static class Node {
    	public int val;
    	public int min;
    	public Node next;
		public Node(int val, int min, Node next) {
			this.val = val;
			this.min = min;
			this.next = next;
		}
    }
}
