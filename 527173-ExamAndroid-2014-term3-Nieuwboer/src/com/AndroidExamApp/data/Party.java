package com.AndroidExamApp.data;

import java.util.HashMap;

public class Party {
	private int _id;
	private String _name;
	private String _description;
	private String _filename;
	private HashMap<String,String> _promises = new HashMap<String,String>();
	private int _plus_sign = 0;
	private int _equals_sign = 0;
	private int _minus_sign = 0;
	
	public Party(String _name, String _description, String _filename,
			HashMap<String, String> _promises) {
		this._name = _name;
		this._description = _description;
		this._filename = _filename;
		this._promises = _promises;
	}
	
	public Party(){
		this._name = "Default";
		this._description = "Default";
		this._filename = "politiek.png";
		_promises.put("Default", "Default");
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_description() {
		return _description;
	}

	public void set_description(String _description) {
		this._description = _description;
	}

	public String get_filename() {
		return _filename;
	}

	public void set_filename(String _filename) {
		this._filename = _filename;
	}

	public HashMap<String, String> get_promises() {
		return _promises;
	}

	public void set_promises(HashMap<String, String> _promises) {
		this._promises = _promises;
	}

	public int get_plus_sign() {
		return _plus_sign;
	}

	public void set_plus_sign(int _plus_sign) {
		this._plus_sign = _plus_sign;
	}
	
	public void set_plus_plus_one() {
		this._plus_sign = this._plus_sign + 1;
	}

	public int get_equals_sign() {
		return _equals_sign;
	}

	public void set_equals_sign(int _equals_sign) {
		this._equals_sign = _equals_sign;
	}
	
	public void set_equals_plus_one() {
		this._equals_sign = this._equals_sign + 1;
	}

	public int get_minus_sign() {
		return _minus_sign;
	}

	public void set_minus_sign(int _minus_sign) {
		this._minus_sign = _minus_sign;
	}
	
	public void set_minus_plus_one() {
		this._minus_sign = this._minus_sign + 1;
	}
	
}