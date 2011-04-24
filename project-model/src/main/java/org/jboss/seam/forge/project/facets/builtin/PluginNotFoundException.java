package org.jboss.seam.forge.project.facets.builtin;

public class PluginNotFoundException extends RuntimeException
{
   public PluginNotFoundException(String groupId, String artifactId) {
      super("Plugin " + groupId + ":" + artifactId + " was not found");
   }
}
