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

package org.jboss.seam.forge.project.facets.builtin;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.jboss.seam.forge.project.Facet;
import org.jboss.seam.forge.project.facets.BaseFacet;
import org.jboss.seam.forge.project.facets.FacetNotFoundException;
import org.jboss.seam.forge.project.facets.MavenCoreFacet;
import org.jboss.seam.forge.project.mavenplugins.MavenPlugin;
import org.jboss.seam.forge.project.mavenplugins.MavenPluginAdapter;
import org.jboss.seam.forge.project.mavenplugins.MavenPluginBuilder;
import org.jboss.seam.forge.shell.plugins.Alias;
import org.jboss.seam.forge.shell.plugins.RequiresFacet;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:paul.bakker.nl@gmail.com">Paul Bakker</a>
 */

@Dependent
@Alias("forge.maven.MavenPluginFacet")
@RequiresFacet(MavenCoreFacet.class)
public class MavenPluginFacetImpl extends BaseFacet implements MavenPluginFacet, Facet
{
   private static final String DEFAULT_GROUPID = "org.apache.maven.plugins";

   @Override public boolean install()
   {
      return true;
   }

   @Override public boolean isInstalled()
   {
      try
      {
         project.getFacet(MavenCoreFacet.class);
         return true;
      } catch (FacetNotFoundException e)
      {
         return false;
      }
   }

   @Override public List<MavenPlugin> listConfiguredPlugins()
   {
      MavenCoreFacet mavenCoreFacet = project.getFacet(MavenCoreFacet.class);
      List<Plugin> pomPlugins = mavenCoreFacet.getPOM().getBuild().getPlugins();
      List<MavenPlugin> plugins = new ArrayList<MavenPlugin>();

      for (Plugin plugin : pomPlugins)
      {
         MavenPluginAdapter adapter = new MavenPluginAdapter(plugin);
         MavenPluginBuilder pluginBuilder = MavenPluginBuilder.create()
                 .setGroupId(plugin.getGroupId())
                 .setArtifactId(plugin.getArtifactId())
                 .setVersion(plugin.getVersion())
                 .setConfiguration(adapter.getPluginConfiguration());

         plugins.add(pluginBuilder);
      }

      return plugins;

   }

   @Override public void addPlugin(MavenPlugin plugin)
   {
      MavenCoreFacet mavenCoreFacet = project.getFacet(MavenCoreFacet.class);
      Model pom = mavenCoreFacet.getPOM();
      pom.getBuild().addPlugin(new MavenPluginAdapter(plugin));
      mavenCoreFacet.setPOM(pom);
   }

   @Override public boolean hasPlugin(String groupId, String artifactId)
   {
      try
      {
         getPlugin(groupId, artifactId);
         return true;
      } catch (PluginNotFoundException ex)
      {
         return false;
      }
   }

   @Override public MavenPlugin getPlugin(String groupdId, String artifactId)
   {
      if (groupdId == null || groupdId.equals(""))
      {
         groupdId = DEFAULT_GROUPID;
      }

      for (MavenPlugin mavenPlugin : listConfiguredPlugins())
      {
         if (mavenPlugin.getGroupId().equals(groupdId) && mavenPlugin.getArtifactId().equals(artifactId))
         {
            return mavenPlugin;
         }
      }

      throw new PluginNotFoundException(groupdId, artifactId);

   }

   @Override public void removePlugin(String groupId, String artifactId)
   {
      MavenCoreFacet mavenCoreFacet = project.getFacet(MavenCoreFacet.class);
      List<Plugin> pomPlugins = mavenCoreFacet.getPOM().getBuild().getPlugins();
      for (Plugin pomPlugin : pomPlugins)
      {
         if (pomPlugin.getGroupId().equals(groupId) && pomPlugin.getArtifactId().equals(artifactId))
         {
            Model pom = mavenCoreFacet.getPOM();
            pom.getBuild().removePlugin(pomPlugin);
            mavenCoreFacet.setPOM(pom);
         }
      }

   }
}
