package org.featherlessbipeds.ashpath.utils;

import org.featherlessbipeds.ashpath.first_task.AdminTest;
import org.featherlessbipeds.ashpath.first_task.CremationQueueTest;
import org.featherlessbipeds.ashpath.first_task.DeathRegistrarTest;
import org.featherlessbipeds.ashpath.first_task.DeceasedTest;
import org.featherlessbipeds.ashpath.first_task.GraveTest;
import org.featherlessbipeds.ashpath.first_task.NecrotomistTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{
    AdminTest.class, DeathRegistrarTest.class, NecrotomistTest.class, CremationQueueTest.class, DeceasedTest.class, GraveTest.class
})
public class TestSuite1
{

}
