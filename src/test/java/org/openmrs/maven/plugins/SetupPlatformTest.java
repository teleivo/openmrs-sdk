package org.openmrs.maven.plugins;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class SetupPlatformTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void determineDbName_shouldReturnDefaultNameIfVariableWasUsed() throws MojoExecutionException {
		SetupPlatform setupPlatform = new SetupPlatform();
		String dbName = setupPlatform.determineDbName("jdbc:mysql://localhost:3016/@DBNAME@?someParam=value", "my_server");
		assertThat(dbName, is(equalTo("openmrs-my_server")));
	}

	@Test
	public void determineDbName_shouldReturnNameFromUriWithParams() throws MojoExecutionException {
		SetupPlatform setupPlatform = new SetupPlatform();
		String dbName = setupPlatform.determineDbName("jdbc:mysql://localhost:3016/open?someParam=value", "my_server");
		assertThat(dbName, is(equalTo("open")));
	}

	@Test
	public void determineDbName_shouldReturnNameFromUriWithoutParams() throws MojoExecutionException {
		SetupPlatform setupPlatform = new SetupPlatform();
		String dbName = setupPlatform.determineDbName("jdbc:mysql://localhost:3016/open", "my_server");
		assertThat(dbName, is(equalTo("open")));
	}

	@Test
	public void determineDbName_shouldFailIfNoNameInUri() throws MojoExecutionException {
		SetupPlatform setupPlatform = new SetupPlatform();

		expectedException.expectMessage("The db name is in a wrong format (allowed ");

		setupPlatform.determineDbName("jdbc:mysql://localhost:3016/", "my_server");

	}
}
