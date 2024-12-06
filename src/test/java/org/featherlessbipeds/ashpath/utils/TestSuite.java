package org.featherlessbipeds.ashpath.utils;

import org.featherlessbipeds.ashpath.AdminTest;
import org.featherlessbipeds.ashpath.CremationQueueTest;
import org.featherlessbipeds.ashpath.DeathRegistrarTest;
import org.featherlessbipeds.ashpath.DeceasedTest;
import org.featherlessbipeds.ashpath.NecrotomistTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{
    AdminTest.class, DeathRegistrarTest.class, NecrotomistTest.class, CremationQueueTest.class, DeceasedTest.class
})
public class TestSuite
{

}
