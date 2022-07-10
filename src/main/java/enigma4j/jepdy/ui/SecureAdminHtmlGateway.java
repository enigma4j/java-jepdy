package enigma4j.jepdy.ui;

import enigma4j.Enigma4JAbstractService;
import enigma4j.jepdy.ui.forms.UploadForm;
import enigma4j.jepdy.model.Category;
import enigma4j.jepdy.model.Clue;
import enigma4j.jepdy.model.User;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.NonBlocking;
import org.jboss.resteasy.reactive.MultipartForm;


import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.util.HashMap;
import java.util.Map;

@Authenticated
@Path("/jepdy/secure/admin")
public class SecureAdminHtmlGateway extends Enigma4JAbstractService  {



    @Inject
    @Location("jepdy/clues.html")
    Template clues;


    @Inject
    @Location("jepdy/users.html")
    Template users;


    @Inject
    @Location("jepdy/categories.html")
    Template categories;


    @Inject
    @Location("jepdy/Admin.html")
    Template admin;


    @GET
    @Path("home")
    @Produces("text/html")
    public TemplateInstance home() {
        return admin.instance();
    }

    @POST
    @NonBlocking
    @Path("upload/csv")
    @Consumes("multipart/form-data")
    @Produces("text/html")
    public TemplateInstance uploadCSV(@MultipartForm UploadForm form)  {

        LOG.info("uploading file requested "+form.file);

        return admin.instance();


    }



    @POST
    @Authenticated
    @Path("addclue/{categoryid}")
    @Produces("text/html")
    @Transactional
    public TemplateInstance   addClue(@PathParam("categoryid") Long catid,@FormParam("clue") String clue,
                                      @FormParam("answer") String answer,
                                      @FormParam("score") String score) {

        LOG.infof("add clue to %d",catid);
        LOG.infof("clue %s",clue);
        LOG.infof("answer %s",answer);
        LOG.infof("score %s",score);

        String error=null;
        Map<String,Object> data=new HashMap<>();

        Category cat=Category.findById(catid);


        if(cat==null) {
            error="missing category";
        } else {
            if(clue==null || clue.trim().equals("")) {
                error="no clue provided";
            } else  if(answer==null || answer.trim().equals("")) {
                error="no answer provided";
            } else if(score==null || clue.trim().equals("")) {
                error="no score provided";
            }
        }

        if(error!=null) {
            data.put("error",error);
            if(cat!=null) {
                data.put("catid",catid);
                data.put("title",cat.title);
                data.put("description",cat.description);
                data.put("clues",cat.entries);
            }
            return clues.data(data);
        }

        Clue c=new Clue();
        c.clue=clue;
        c.answer=answer;
        c.value=Integer.parseInt(score);
        c.persist();
        cat.entries.add(c);
        cat.persist();

        LOG.infof("category has %d clues",cat.entries.size());

        data.put("catid",catid);
        data.put("title",cat.title);
        data.put("description",cat.description);
        data.put("clues",cat.entries);


        return clues.data(data);

    }


    @GET
    @Authenticated
    @Path("editcategory/{id}")
    @Produces("text/html")
    @Transactional
    public TemplateInstance editcategory(@PathParam("id") Long id) {

        Category cat=Category.findById(id);
        Map<String,Object> data=new HashMap<>();
        if(cat==null) {
            data.put("error","category unknown");
        } else {
            data.put("title",cat.title);
            data.put("description",cat.description);
            data.put("catid",id);
            data.put("clues",cat.entries);
        }

        return clues.data(data);
    }



    @POST
    @Authenticated
    @Path("adduser")
    @Produces("text/html")
    @Transactional
    public TemplateInstance   addUser(@FormParam("login") String login) {

        LOG.info("add user "+login);

        if(login!=null) {
            login=login.trim();
            if(login!="") {
                User eu=new User();
                eu.login=login;
                eu.persist();
                LOG.info("user "+login+"persisted");
            }
        }
        return users.instance();

    }

    @POST
    @Authenticated
    @Path("addcategory")
    @Produces("text/html")
    @Transactional
    public TemplateInstance   addCategory(@FormParam("category") String category) {

        LOG.info("add category "+category);

        if(category!=null) {
            category=category.trim();
            if(category!="") {
                Category cat=new Category();
                cat.title=category;
                cat.persist();
                LOG.info("category "+cat+"persisted");
            }
        }
        return categories.instance();

    }


    @GET
    @Authenticated
    @Path("categories")
    @Produces("text/html")
    public TemplateInstance categories() {

        return categories.instance();
    }


    @GET
    @Authenticated
    @Path("users")
    @Produces("text/html")
    public TemplateInstance users() {

        LOG.info("users");
        return users.instance();
    }

}
