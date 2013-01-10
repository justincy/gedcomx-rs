/**
 * Copyright 2011-2012 Intellectual Reserve, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gedcomx.rs;

import org.gedcomx.Gedcomx;
import org.gedcomx.common.Note;
import org.gedcomx.conclusion.*;
import org.gedcomx.rt.GedcomxConstants;
import org.gedcomx.rt.rs.*;
import org.gedcomx.source.SourceReference;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * <p>The person resource defines the interface for a person, including the components of a person such as the person's names, gender, facts, source references, and notes.
 * The person resource also includes the relationships in which the person is a member, such as the relationships to parents, spouses, and children.</p>
 *
 * <p>The person resource MAY contain links to other resources that are to be considered "embedded", meaning the resources being linked to are to be considered
 * as components of the person resource and MUST be resolved and embedded in order to fully resolve all of the components of the person.</p>
 *
 * @author Ryan Heaton
 */
@ResourceDefinition (
  resourceElement = Gedcomx.class,
  projectId = "gedcomx-rs",
  namespace = GedcomxConstants.GEDCOMX_NAMESPACE,
  states = {
    @StateDefinition (
      name = "Person",
      rel = PersonRSDefinition.REL,
      description = "A person.",
      transitions = {
        @StateTransition ( rel = ConclusionRSDefinition.REL_PERSON, description = "A conclusion.", scope = { Name.class, Gender.class, Fact.class }, conditional = true ),
        @StateTransition ( rel = ConclusionsRSDefinition.REL_PERSON, description = "The conclusions for the person (embedded link).", scope = Person.class, conditional = true ),
        @StateTransition ( rel = SourceReferencesRSDefinition.REL_PERSON, description = "The source references for the person (embedded link).", scope = Person.class, conditional = true),
        @StateTransition ( rel = SourceReferenceRSDefinition.REL_PERSON, description = "A source reference.", scope = SourceReference.class, conditional = true),
        @StateTransition ( rel = NotesRSDefinition.REL_PERSON, description = "The notes for the person (embedded link).", scope = Person.class, conditional = true),
        @StateTransition ( rel = NoteRSDefinition.REL_PERSON, description = "A note.", scope = Note.class, conditional = true),
        @StateTransition ( rel = RelationshipRSDefinition.REL, description = "A relationship.", scope = Relationship.class ),
        @StateTransition ( rel = PersonRelationshipsRSDefinition.SPOUSE_RELATIONSHIPS_REL, description = "The relationships to the spouses of the person (embedded link).", scope = Person.class, conditional = true ),
        @StateTransition ( rel = PersonRelationshipsRSDefinition.CHILD_RELATIONSHIPS_REL, description = "The relationships to the children of the person (embedded link).", scope = Person.class, conditional = true ),
        @StateTransition ( rel = PersonRelationshipsRSDefinition.PARENT_RELATIONSHIPS_REL, description = "The relationships to the parents of the person (embedded link).", scope = Person.class, conditional = true )
      }
    )
  }
)
public interface PersonRSDefinition {

  public static final String REL = "person";

  /**
   * Read a person header attributes.
   *
   * @return The header attributes for the person.
   */
  @HEAD
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 301, condition = "If the requested person has been merged to another person."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
  })
  Response head();

  /**
   * Read a person.
   *
   * @return The person.
   */
  @GET
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 301, condition = "If the requested person has been merged into another person."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
  })
  Response get();

  /**
   * Update a person.
   *
   * @param person The person to be used for the update.
   *
   */
  @POST
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The update was successful."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
  })
  Response post(Gedcomx person);

  /**
   * Delete a person.
   *
   */
  @DELETE
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The delete was successful."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has already been deleted.")
  })
  Response delete();

}
