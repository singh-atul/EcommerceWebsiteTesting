package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import dataProvider.ConfigFileReader;
import objectManager.DriverManager;

public class Listener implements ITestListener
{
	@Override
	public void onFinish(ITestContext context) 
	{
		System.out.println("On Finish");
		try 
		{
			//Closing webdriver
			DriverManager.getDriver().close();
			//Killing all chrome instances
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		} 
		catch (Exception e) 
		{
			e.getMessage();
		}
	}
	@Override
	public void onTestStart(ITestResult result) 
	{	
		DriverManager.getDriver().get(ConfigFileReader.getUrl());
	}

	@Override
	public void onTestSuccess(ITestResult result) {}

	@Override
	public void onTestFailure(ITestResult result) {}

	@Override
	public void onTestSkipped(ITestResult result){}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

	@Override
	public void onStart(ITestContext context) {}

}
