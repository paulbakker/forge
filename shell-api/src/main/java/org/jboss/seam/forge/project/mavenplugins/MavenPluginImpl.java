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

/**
 * @author <a href="mailto:paul.bakker.nl@gmail.com">Paul Bakker</a>
 */
public class MavenPluginImpl implements MavenPlugin
{
   private String groupId;
   private String artifactId;
   private String version;
   private MavenPluginConfiguration configuration;

   public MavenPluginImpl()
   {
   }

   public MavenPluginImpl(MavenPlugin plugin) {
      this.groupId = plugin.getGroupId();
      this.artifactId = plugin.getArtifactId();
      this.version = plugin.getVersion();
      this.configuration = plugin.getPluginConfiguration();
   }

   @Override
   public String getGroupId()
   {
      if(groupId == null) {
         return "org.apache.maven.plugins";
      }

      return groupId;
   }

   public void setGroupId(String groupId)
   {
      this.groupId = groupId;
   }

   @Override
   public String getArtifactId()
   {
      return artifactId;
   }

   public void setArtifactId(String artifactId)
   {
      this.artifactId = artifactId;
   }

   @Override
   public String getVersion()
   {
      return version;
   }

   public void setVersion(String version)
   {
      this.version = version;
   }

   @Override public MavenPluginConfiguration getPluginConfiguration()
   {
      return configuration;
   }

   @Override public String toString()
   {
      StringBuilder b = new StringBuilder("<plugin>");
      if(groupId != null) {
         b.append("<groupId>").append(groupId).append("</groupId>");
      }

      if(artifactId != null) {
         b.append("<artifactId>").append(artifactId).append("</artifactId>");
      }

      if(version != null) {
         b.append("<version>").append(version).append("</version>");
      }

      if(configuration != null) {
         b.append(configuration.toString());
      }

      b.append("</plugin>");
      return b.toString();
   }

   public void setConfiguration(MavenPluginConfiguration configuration)
   {
      this.configuration = configuration;
   }
}
