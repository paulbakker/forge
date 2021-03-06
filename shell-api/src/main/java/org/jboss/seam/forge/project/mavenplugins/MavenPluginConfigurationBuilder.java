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

import java.util.List;

/**
 * @author <a href="mailto:paul.bakker.nl@gmail.com">Paul Bakker</a>
 */
public class MavenPluginConfigurationBuilder implements MavenPluginConfiguration
{
   private MavenPluginConfigurationImpl mavenPluginConfiguration = new MavenPluginConfigurationImpl();
   private MavenPluginBuilder origin;

   @Override public boolean hasConfigurationElement(String configElement)
   {
      return mavenPluginConfiguration.hasConfigurationElement(configElement);
   }

   @Override public List<MavenPluginConfigurationElement> listConfigurationElements()
   {
      return mavenPluginConfiguration.listConfigurationElements();
   }

   @Override public MavenPluginConfiguration addConfigurationElement(MavenPluginConfigurationElement element)
   {
      return mavenPluginConfiguration.addConfigurationElement(element);
   }

   @Override public String toString()
   {
      return mavenPluginConfiguration.toString();
   }

   private MavenPluginConfigurationBuilder() {

   }

   public MavenPluginConfigurationElementBuilder createConfigurationElement(String name)
   {
      MavenPluginConfigurationElementBuilder builder = MavenPluginConfigurationElementBuilder.create(this);
      builder.setName(name);
      mavenPluginConfiguration.addConfigurationElement(builder);
      return builder;
   }

   private MavenPluginConfigurationBuilder(MavenPluginBuilder pluginBuilder) {
      origin = pluginBuilder;
   }


   private MavenPluginConfigurationBuilder(MavenPluginConfiguration existingConfig, MavenPluginBuilder pluginBuilder) {
      origin = pluginBuilder;
      for (MavenPluginConfigurationElement element : existingConfig.listConfigurationElements())
      {
         mavenPluginConfiguration.addConfigurationElement(element);
      }
   }

   public static MavenPluginConfigurationBuilder create() {
      return new MavenPluginConfigurationBuilder();
   }

   public static MavenPluginConfigurationBuilder create(MavenPluginBuilder pluginBuilder) {
      return new MavenPluginConfigurationBuilder(pluginBuilder);
   }

   public static MavenPluginConfigurationBuilder create(MavenPluginConfiguration existingConfig, MavenPluginBuilder pluginBuilder) {
      return new MavenPluginConfigurationBuilder(existingConfig, pluginBuilder);
   }

   public MavenPluginBuilder getOrigin()
   {
      return origin;
   }
}
