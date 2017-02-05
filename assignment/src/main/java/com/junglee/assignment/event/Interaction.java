package com.junglee.assignment.event;

public enum Interaction {
	
	HANDSHAKE_COMPLETE_SUCCESS(1),
	LOGIN(2),
	LOGIN_DONE(3),
	NEW_USER_LOGIN_DONE(4),
	PLAY(5),
	PLAY_DONE(6),
	LEAVE(7);
	
	private int num;

	Interaction(int num){
		this.num = num;
	}
	
	public int getNum(){
		return num;
	}
}
