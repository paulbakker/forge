/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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

package org.jboss.seam.forge.project.mavenplugins;

import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:paul.bakker.nl@gmail.com">Paul Bakker</a>
 */
public class MavenPluginAdapterTest
{

   @Test
   public void testGetPluginConfiguration() throws Exception
   {
      MavenPluginAdapter adapter = new MavenPluginAdapter(createMavenPlugin());
      MavenPluginConfiguration pluginConfiguration = adapter.getConfig();
      assertNotNull(pluginConfiguration);
   }


   private Plugin createMavenPlugin() throws Exception
   {
      Plugin plugin = new Plugin();
      plugin.setGroupId("org.apache.maven.plugins");
      plugin.setGroupId("maven-site-plugin");
      plugin.setVersion("3.0");
      Xpp3Dom dom;
      dom = Xpp3DomBuilder.build(
              new ByteArrayInputStream(
                      ("<configuration>" +
                              "   <reportPlugins>" +
                              "       <plugin>" +
                              "           <groupId>org.codehaus.mojo</groupId>" +
                              "           <artifactId>findbugs-maven-plugin</artifactId>" +
                              "           <version>2.3.2</version>" +
                              "       </plugin>" +
                              "   </reportPlugins>" +
                              "</configuration>").getBytes()),
              "UTF-8");

      plugin.setConfiguration(dom);
      return plugin;
   }


}
