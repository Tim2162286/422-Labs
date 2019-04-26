package edu.asupoly.ser422.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asupoly.ser422.lab3.PhoneEntryResource;
import edu.asupoly.ser422.service.PhoneBookFactory;
import edu.asupoly.ser422.service.PhoneBookService;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;




@Path("/")
@Produces({MediaType.APPLICATION_JSON})
public class PhoneBookRest {
    @Context
    private UriInfo uriInfo;
    PhoneBookService pbook = PhoneBookFactory.getInstance();

    /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */
    /** @apiDefine NotFoundError
     * @apiError (Error 4xx) {404} NotFound Resource cannot be found
     * */
    /**
     * @apiDefine InternalServerError
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
     * */
    /**
     * @apiDefine ForbiddenError
     * @apiError (Error 4xx) {403} Forbidden Activity cannot be preformed
     */
    /**
     * @apiDefine ConflictError
     * @apiError (Error 4xx) {409} Conflict Resource already exists
     */

    /**
     * @api {post} /phonebook Creates a new Phonebook
     * @apiName createPhoneBook
     * @apiGroup PhoneBook
     *
     * @apiUse ConflictError
     * @apiUse InternalServerError
     *
     * @apiParam {String} book Name of book to create
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 201 Created
     */
    @POST
    @Path("phonebook/{book}")
    public Response createPhoneBook(@PathParam("book") String book){
        int res = pbook.createPhoneBook(book);
        if(res==1)
            return Response.status(Response.Status.CREATED).build();
        if(res==-1)
            return Response.status(Response.Status.CONFLICT).entity("Error 409\n\nPhonebook already exists").build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    //Gets all entries in a phonebook matching the first and last Query Parameters
    /**
     * @api {get} /phonebook/:book Gets entries from a specific Phonebook
     * @apiName getPhonebook
     * @apiGroup Phonebook
     *
     * @apiUse NotFoundError
     * @apiUse InternalServerError
     *
     * @apiParam {String} book Name of book to get entries in
     * @apiParam {String} [first] Substring to match first names against
     * @apiParam {String} [last] Substring to match last names against
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 200 OK
     *  [
     *      {"firstname":"Jane","lastname":"Doe","phone":"324234"},
     *      {"firstname":"John","lastname":"Doe","phone":"325423"}
     *  ]
     */
    @GET
    @Path("phonebook/{book}")
    public Response getPhonebook(@PathParam("book") String name,
                                 @DefaultValue("") @QueryParam("first") String fName,
                                 @DefaultValue("") @QueryParam("last") String lName){
        List<PhoneEntryResource> listings = pbook.getBookEntries(name);
        if(listings!=null) {
            List<PhoneEntryResource> entries = new ArrayList<>();
            for(PhoneEntryResource e:listings){
                if(e.firstname.contains(fName)&&e.lastname.contains(lName)){
                    entries.add(e);
                }
            }
            try {
                String resp = new ObjectMapper().writeValueAsString(entries);
                return Response.status(Response.Status.OK).entity(resp).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
        //return listings;
    }


    //Puts an unlisted entry into a phonebook
    /**
     * @api {put} /phonebook/:book puts an unlisted entry into a phonebook
     * @apiName addToPhonebook
     * @apiGroup Phonebook
     *
     * @apiUse NotFoundError
     * @apiUse InternalServerError
     * @apiUse ForbiddenError
     *
     * @apiParam {String} book Name of book to add entry to
     * @apiParam {text/plain} number Phone number of the entry to insert into the phonebook
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 204 NoContent
     *  Entry placed in phonebook "Book1"
     */
    @PUT
    @Path("phonebook/{book}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addToPhoneBook(String number, @PathParam("book") String book){
        int res = pbook.addToBook(number,book);
        if(res == -3)
            return Response.status(Response.Status.FORBIDDEN).entity("Error 403\n\nThis entry is already part of a Phonebook.").build();
        if(res == -2)
            return Response.status(Response.Status.NOT_FOUND).entity("Error 404\n\nCould not find an entry with the given number").build();
        if(res == -1)
            return Response.status(Response.Status.NOT_FOUND).entity("Error 404\n\nCould not find the requested Phonebook").build();
        if(res==1)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }


    //Get lab3 entry by number
    /**
     * @api {get} /entry/:number Gets an entry by phone number
     * @apiName getEntry
     * @apiGroup Entry
     *
     * @apiUse NotFoundError
     * @apiUse InternalServerError
     *
     * @apiParam {String} number Phone number of the entry to retrieve
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 200 OK
     *  {
     *      "firstname":"John",
     *      "lastname":"Doe",
     *      "phone":"1234567890"
     *   }
     */
    @GET
    @Path("entry/{number}")
    public Response getEntry(@PathParam("number") String number){
        System.out.println("Getting entry");
        PhoneEntryResource entry = pbook.getEntry(number);
        try{
            if(entry!=null){
                String resp = new ObjectMapper().writeValueAsString(entry);
                return Response.status(Response.Status.OK).entity(resp).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Error 404\n\nNo entry found for given lab3 number").build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    }


    // Creates a new lab3 entry
    /**
     * @api {post} /entry Creates a new unlisted entry
     * @apiName createEntry
     * @apiGroup Entry
     *
     * @apiUse BadRequestError
     * @apiUse ConflictError
     * @apiUse InternalServerError
     *
     * @apiParam {json} entry JSON representation the entry you wish to create
     * @apiParamExample {json} entry:
     * {
     *  "phone":"1234",
     *  "firstname":"John",
     *  "lastname":"Doe"
     * }
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 201 Created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("entry")
    public Response createEntry(PhoneEntryResource entry){
        if (entry.invalid())
            return Response.status(Response.Status.BAD_REQUEST).entity("Error 400\n\nPOST body was missing 1 or more required parameters, or was not" +
                    " an acceptable format. Post body should be Json containing the fields \"lab3\", \"firstname\", and \"lastname\".").build();
        int res = pbook.newEntry(entry);
        if(res==1)
            return Response.status(Response.Status.CREATED).build();
        if(res==-1)
            return Response.status(Response.Status.CONFLICT).entity("Error 409\n\nEntry with given phone number already exists").build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }


    // Updates first and/or last name for an entry
    /**
     * @api {put} /entry/:number Updates first and/or last name in an entry
     * @apiName updateEntry
     * @apiGroup Entry
     *
     * @apiUse NotFoundError
     * @apiUse InternalServerError
     *
     * @apiParam {String} number Phone number of the entry to update
     * @apiParam {json} update Json containing fields to update entry with (firstname and/or lastname)
     * @apiParamExample {json} update:
     *  {
     *      "firstname":"Doe",
     *      "lastname":Jane"
     *  }
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 204 NoContent
     */
    @PUT
    @Path("entry/{number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEntry(PhoneEntryResource update,@PathParam("number") String number){
        PhoneEntryResource entry = pbook.getEntry(number);
        System.out.println(update.toString());
        if(entry==null)
            return Response.status(Response.Status.NOT_FOUND).entity("Error 404\n\nThe entry you are trying to modify does not exist").build();
        int res = pbook.updateEntry(entry, update);
        if (res == 1)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }


    // Deletes an entry
    /**
     * @api {delete} entry/:number Delete an entry
     * @apiName deleteEntry
     * @apiGroup Entry
     * @apiParam {String} number phoneNumber of entry to delete
     *
     * @apiUse NotFoundError
     * @apiUse InternalServerError
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 204 NoContent
     */
    @DELETE
    @Path("entry/{number}")
    public Response deleteEntry(@PathParam("number") String number){
        int res = pbook.deleteEntry(number);
        if(res==1)
            return Response.status(Response.Status.NO_CONTENT).build();
        if(res == -1)
            Response.status(Response.Status.NOT_FOUND).entity("Error 404\n\nThe entry you are attempting to delete does not exist").build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }


    // Gets any unlisted entries
    /**
     * @api {get} /entries/unlisted Gets all unlisted entries
     * @apiName getUnlisted
     * @apiGroup Entries
     *
     * @apiUse InternalServerError
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 200 OK
     *  [
     *      {"firstname":"Jane","lastname":"Doe","phone":"324234"},
     *      {"firstname":"John","lastname":"Doe","phone":"325423"}
     *  ]
     */
    @GET
    @Path("entries/unlisted")
    public Response getEntries(){
        List<PhoneEntryResource> entries = pbook.getUnlisted();
        if(entries!=null){
            try {
                String resp = new ObjectMapper().writeValueAsString(entries);
                return Response.status(Response.Status.OK).entity(resp).build();
            } catch (Exception e){e.printStackTrace();}
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
