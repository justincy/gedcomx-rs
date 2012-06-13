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

import org.gedcomx.atom.Feed;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

/**
 * <p>The search resource defines the set of entries in the system that are applicable to specific search criteria.</p>
 *
 * <p>Links to the search resource should be defined using a URI template as defined by <a href="http://tools.ietf.org/html/draft-gregorio-uritemplate">the
 * latest URI template specification draft</a>. Implementations are free to choose which parameters are supported and how to apply them to their
 * URI space, but the following parameters are reserved for conformance to the GEDCOM X specification and are defined as follows:</p>
 *
 * <table>
 *   <tr>
 *     <th>variable</th>
 *     <th>description</th>
 *   </tr>
 *   <tr>
 *     <td>start</td>
 *     <td>The index of the first search result desired by the search client.</td>
 *   </tr>
 *   <tr>
 *     <td>count</td>
 *     <td>The number of search results per page desired by the search client.</td>
 *   </tr>
 *   <tr>
 *     <td>q</td>
 *     <td><p>The query parameter describing the search criteria. A parameter name and value is separated by a colon ':' and each name value pair is separated
 *     by a white space '&nbsp;'.</p>
 *     For example:<br/>
 *     <h4>q=giveName:John surname:Smith gender:male birthDate:"30 June 1900"</h4><br/>
 *     <p>Notice the white space in the birthDate value.  If white space is needed in the value then the value must be wrapped in double quotes. By default
 *     values are exact. For non-exact matches append a tilde '~' at the end of the value such as givenName:Bob~.</p>
 *       <table class="param-table">
 *         <tr>
 *           <th>name</td>
 *           <th>description</td>
 *         </tr>
 *         <tr>
 *           <td>givenName:</td>
 *           <td>The given name of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>surname:</td>
 *           <td>The family name of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>gender:</td>
 *           <td>The gender of the person being searched. Valid values are "<tt>male</tt>" and "<tt>female</tt>".</td>
 *         </tr>
 *         <tr>
 *           <td>birthDate:</td>
 *           <td>The birth date of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>birthPlace:</td>
 *           <td>The birth place of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>deathDate:</td>
 *           <td>The death date of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>deathPlace:</td>
 *           <td>The death place of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>marriageDate:</td>
 *           <td>The marriage date of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>marriagePlace:</td>
 *           <td>The marriage place of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td colspan="2">
 *             <h4>Relation Search Parameters</h4>
 *             <p>The following set of standard parameters is defined as the substitution of <tt>{relation}</tt> with all of the
 *             values "<tt>father</tt>", "<tt>mother</tt>", "<tt>spouse</tt>", and "<tt>parent</tt>".</p>
 *           </td>
 *         </tr>
 *         <tr>
 *           <td>{relation}GivenName:</td>
 *           <td>The given name of the {relation} of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>{relation}Surname:</td>
 *           <td>The family name of the {relation} of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>{relation}BirthDate:</td>
 *           <td>The birth date of the {relation} of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>{relation}BirthPlace:</td>
 *           <td>The birth place of the {relation} of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>{relation}DeathDate:</td>
 *           <td>The death date of the {relation} of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>{relation}DeathPlace:</td>
 *           <td>The death place of the {relation} of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>{relation}MarriageDate:</td>
 *           <td>The marriage date of the {relation} of the person being searched.</td>
 *         </tr>
 *         <tr>
 *           <td>{relation}MarriagePlace:</td>
 *           <td>The marriage place of the {relation} of the person being searched.</td>
 *         </tr>
 *       </table>
 *     </td>
 *   </tr>
 * </table>
 *
 * @author Ryan Heaton
 */
@ResourceDefinition (
  name = "Search",
  projectId = RSModel.RS_PROJECT_ID,
  namespace = RSModel.RS_V1_NAMESPACE,
  resourceElement = Feed.class,
  subresources = { PersonEntryRSDefinition.class }
)
@ResourceLinks ( {
})
public interface SearchRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "search";

  /**
   * Read the results of a search.
   *
   * @return The search results.
   */
  @GET
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 204, condition = "Upon a successful query with no results."),
    @ResponseCode ( code = 400, condition = "If the query to be processed was unable to be understood by the application."),
    @ResponseCode ( code = 400, condition = "If the application declines to process the query because it would have resulted in too many results.")
  })
  @Warnings({
    @ResponseCode( code = 299, condition = "If part or all of the query is unable to be processed.")
  })
  Response get();

}
