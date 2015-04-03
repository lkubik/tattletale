/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.tattletale;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The version class
 * 
 * @author Jesper Pedersen <jesper.pedersen@jboss.org>
 */
public class Version {

	/** The vendor */
	public static final String VENDOR = "JBoss";

	/** The product */
	public static final String PRODUCT = "Tattletale";

	/** The version */
	private static String VERSION = "@VERSION@";

	/** Full version */
	public static final String FULL_VERSION = VENDOR + " " + PRODUCT + " "
			+ getVersion();

	public static boolean initialized = false;

	/**
	 * Constructor
	 */
	private Version() {
	}

	/**
	 * Main
	 * 
	 * @param args
	 *            The arguments
	 */
	public static void main(String[] args) {
		System.out.println(PRODUCT + " " + VERSION);
	}

	public static String getVersion() {
		if (initialized) {
			return VERSION;
		} else {
			Properties prop = new Properties();
			InputStream input = null;
			try {
				String filename = "version.properties";
				input = Version.class.getClassLoader().getResourceAsStream(
						filename);
				if (input == null) {
					System.out.println("Sorry, unable to find " + filename);
					VERSION = "";
					initialized = true;
					return VERSION;
				}

				// load a properties file from class path, inside static method
				prop.load(input);
				VERSION = prop.getProperty("maven.version");

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			initialized = true;
			return VERSION;
		}
	}
}
