package com.automation.store.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OrderTest.class, AccountDetailTest.class, EmptyCartMessageTest.class })
public class AllTests {
}
