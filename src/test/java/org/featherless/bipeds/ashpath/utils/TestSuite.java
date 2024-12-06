package org.featherless.bipeds.ashpath.utils;

import org.featherless.bipeds.ashpath.AdminTest;
import org.featherless.bipeds.ashpath.DeathRegistrarTest;
import org.featherless.bipeds.ashpath.NecrotomistTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{
    AdminTest.class, DeathRegistrarTest.class, NecrotomistTest.class
})
public class TestSuite
{

}
