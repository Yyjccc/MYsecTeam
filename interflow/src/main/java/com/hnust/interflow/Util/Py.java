package com.hnust.interflow.Util;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public  class Py {

	public static PythonInterpreter interpreter=new PythonInterpreter();


	public static void test(){
		interpreter.execfile("./src/main/resources/py/main.py");
		PyFunction function=interpreter.get("test",PyFunction.class);
		PyObject pyObject = function.__call__();
		System.out.println(pyObject.toString());
	}

	public static String pyCall(String funName){
		interpreter.execfile("./src/main/resources/py/main.py");
		PyFunction function=interpreter.get(funName,PyFunction.class);
		PyObject pyObject = function.__call__();
		return pyObject.toString();
	}
	public static String pyCall(String funName,String args1){
		interpreter.execfile("./src/main/resources/py/wenxin.py");
		PyFunction function=interpreter.get(funName,PyFunction.class);
		PyObject pyObject = function.__call__(new PyString(args1));
		return pyObject.toString();
	}


}
