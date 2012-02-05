package com.buildbotwatcher.worker;

import java.sql.Timestamp;

public class Step {
	private boolean		_isFinished;
	private boolean		_isStarted;
	private String		_name;
	private int			_stepNumber;
	private String		_text;
	private Timestamp	_timeStart, _timeEnd;

	public Step(boolean isFinished, boolean isStarted, String name, int stepNumber, String text, Timestamp timeStart, Timestamp timeEnd) {
		_isFinished = isFinished;
		_isStarted = isStarted;
		_name = name;
		_stepNumber = stepNumber;
		_text = text;
		_timeStart = timeStart;
		_timeEnd = timeEnd;
	}

	public boolean isFinished() {
		return _isFinished;
	}

	public boolean isStarted() {
		return _isStarted;
	}

	public String getName() {
		return _name;
	}

	public int getStepNumber() {
		return _stepNumber;
	}

	public String getText() {
		return _text;
	}

	public Timestamp getTimeStart() {
		return _timeStart;
	}

	public Timestamp getTimeEnd() {
		return _timeEnd;
	}
}
