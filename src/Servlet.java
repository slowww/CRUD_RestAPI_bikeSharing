import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mariadb.jdbc.MariaDbDataSource;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

public class Servlet extends javax.servlet.http.HttpServlet {

    String tag="";
    String stato="";
    String id_tess="";
    String nome="";
    String cogn="";
    String ind="";
    String mail="";
    int sec_cred;
    String pwd="";
    String id_staz="";
    boolean disp;
    boolean ndisp;
    String ind_staz="";
    //java.util.Date data_start;
    String data_end="";




    @SuppressWarnings("Duplicates")
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        response.setContentType("application/json");


        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setServerName("localhost");
            dataSource.setUser("root");
            dataSource.setPassword("");
            dataSource.setDatabaseName("bike_db");


            Connection conn;
            //Statement stmt;
            PreparedStatement stmt;


            conn = dataSource.getConnection();
            //stmt = conn.createStatement();

            Enumeration<String> parameterNames = request.getParameterNames();//SET DEI POSSIBILI VALORI DELLA REQUEST (nel nostro caso: isbn - titolo - autore - casaed)

            String paramValue;
            String paramName;

            while(parameterNames.hasMoreElements()) {

                paramName = parameterNames.nextElement();
                paramValue = request.getParameter(paramName);//ottengo il valore del parametro che ha nome *paramName*

                switch(paramName) {
                    case "tag":
                        if (paramValue != null) {
                            tag = paramValue;
                        }
                        break;
                    case "stato":
                        if (paramValue != null) {
                            stato = paramValue;
                        }
                        break;
                    case "id_tess":
                        if (paramValue != null) {
                            id_tess = paramValue;
                        }
                        break;
                    case "nome":
                        if (paramValue != null) {
                            nome = paramValue;
                        }
                        break;
                    case "cogn":
                        if (paramValue != null) {
                            cogn = paramValue;
                        }
                        break;
                    case "ind":
                        if (paramValue != null) {
                            ind = paramValue;
                        }
                        break;
                    case "mail":
                        if (paramValue != null) {
                            mail = paramValue;
                        }
                        break;
                    case "sec_cred":
                        if (paramValue != null) {
                            sec_cred = Integer.parseInt(paramValue);
                        }
                        break;
                    case "pwd":
                        if (paramValue != null) {
                            pwd = paramValue;
                        }
                        break;
                    case "id_staz":
                        if (paramValue != null) {
                            id_staz = paramValue;
                        }
                        break;
                    case "disp":
                        if (paramValue != null) {
                            disp = Boolean.parseBoolean(paramValue);
                        }
                        break;
                    case "ndisp":
                        if (paramValue != null) {
                            ndisp = Boolean.parseBoolean(paramValue);
                        }
                        break;
                    case "ind_staz":
                        if (paramValue != null) {
                            ind_staz = paramValue;
                        }
                        break;
                    /*case "data_start":
                        if (paramValue != null) {
                            data_start = new SimpleDateFormat("dd/MM/yy").parse(paramValue);
                        }
                        break;
                    case "data_end":
                        if (paramValue != null) {
                            data_end = paramValue;
                        }
                        break;*/
                }


            }


            /*****CHECK ENDPOINT*****/

            switch(request.getRequestURI())
            {
                case "/bike":
                    stmt = conn.prepareStatement("INSERT INTO `bici`(`tag`, `stato`) VALUES (?,?);");
                    stmt.setString(1,tag);
                    stmt.setString(2,stato);
                    int value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);


                    stmt.close();
                    conn.close();
                    break;
                case "/utente":
                    stmt = conn.prepareStatement("INSERT INTO `utenti`(`id_tess`, `nome`, `cogn`,`ind`,`mail`,`sec_cred`,`pwd`) VALUES (?,?,?,?,?,?,?);");
                    stmt.setString(1,id_tess);
                    stmt.setString(2,nome);
                    stmt.setString(3,cogn);
                    stmt.setString(4,ind);
                    stmt.setString(5,mail);
                    stmt.setInt(6,sec_cred);
                    stmt.setString(7,pwd);
                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);


                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);


                    stmt.close();
                    conn.close();
                    break;
                case "/staz":
                    stmt = conn.prepareStatement("INSERT INTO `stazioni`(`id_staz`, `disp`, `ndisp`,`ind_staz`) VALUES (?,?,?,?);");


                    stmt.setString(1,id_staz);
                    stmt.setBoolean(2,disp);
                    stmt.setBoolean(3,ndisp);
                    /*
                    * true/true stazione piena a metà
                    * true/false stazione vuota
                    * false/true stazione piena
                    * false/false impossibile (chiusa?)
                    *
                    * */


                    stmt.setString(4,ind_staz);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);

                    stmt.close();
                    conn.close();
                    break;
                case "/nol":
                    stmt = conn.prepareStatement("INSERT INTO `noleggi`(`id_tess_fk`, `tag_fk`) VALUES (?,?);");
                    stmt.setString(1,id_tess);
                    stmt.setString(2,tag);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }


                    response.getWriter().println(jsonArray);
                    stmt.close();
                    conn.close();
                    break;
                case "/park":
                    stmt = conn.prepareStatement("INSERT INTO `parcheggi`(`tag_fk`,`id_staz_fk`) VALUES(?,?);");
                    stmt.setString(1,tag);
                    //stmt.setNull(2, Types.NULL);//OK? sulla tabella verrà valorizzato il campo con un timestamp automatico (settare TIMESTAMP - CURRENT TIMESTAMP)
                    stmt.setString(2,id_staz);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);

                    stmt.close();
                    conn.close();
                    break;
            }



        } catch (SQLException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }





    }

    @SuppressWarnings("Duplicates")
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    //PER AUTENTICAZIONE LOGIN
        int count;


        try {

            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setServerName("localhost");
            dataSource.setDatabaseName("bike_db");
            dataSource.setUser("root");
            dataSource.setPassword("");

            Connection conn = dataSource.getConnection();

            ResultSet rs;
            response.setContentType("application/json");
            JSONArray jsonArray = new JSONArray();



            /*A ResultSet is a Java object that contains the results of executing an SQL query. In other words, it contains the rows that satisfy the conditions of the query.
             * The data stored in a ResultSet object is retrieved through a set of get methods that allows access to the various columns of the current row.*/


            Enumeration<String> parameterNames = request.getParameterNames();//SET DEI POSSIBILI VALORI DELLA REQUEST (nel nostro caso: isbn - titolo - autore - casaed)
            String paramValue;
            String paramName;


            while(parameterNames.hasMoreElements())
            {
                paramName = parameterNames.nextElement();
                paramValue = request.getParameter(paramName);//ottengo il valore del parametro che ha nome *paramName*

                switch (paramName)
                {
                    case "id_tess":
                        if (paramValue != null)
                        {
                            id_tess = paramValue;
                        }
                        break;
                    case "pwd":
                        if (paramValue != null)
                        {
                            pwd = paramValue;
                        }
                        break;
                    case "id_staz":
                        if (paramValue != null)
                        {
                            id_staz = paramValue;
                        }
                        break;

                }//end switch
            }//end while

                switch(request.getRequestURI())
                {
                    case "/utente":
                        if(id_tess.isEmpty()&&pwd.isEmpty())//se id_tess e pwd non sono stati specificati è un get generico (lista di tutti gli utenti in json)
                        {
                            try
                            {
                                Statement stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT * FROM utenti"); //MOSTRA RISULTATO DELLA QUERY
                                //int i=0;
                                try
                                {
                                    while (rs.next())
                                    {
                                        ResultSetMetaData metaData = rs.getMetaData();//ottengo nomi colonne tabella
                                        count = metaData.getColumnCount();//conto le colonne
                                        JSONObject jsonObject = new JSONObject();
                                        for (int a = 1; a<= count; a++)//CREO L'OGGETTO (un singolo utente)
                                        {
                                            try
                                            {
                                                jsonObject.put(metaData.getColumnName(a), rs.getObject(a));//inserisco key e value nel jsonobject
                                            } catch (JSONException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }

                                        jsonArray.put(jsonObject);//metto l'oggetto creato nell'array

                                        //i++;

                                    }//end while
                                }finally
                                {
                                    rs.close();
                                }

                                response.getWriter().println(jsonArray);//stampo
                                stmt.close();
                                conn.close();
                            }catch (SQLException e){e.printStackTrace();}
                        }else{//se id_tess e pwd sono stati specificati (login)
                            try
                            {
                                PreparedStatement stmt;
                                JSONObject jsonObject = new JSONObject();
                                stmt = conn.prepareStatement("SELECT * FROM `utenti` WHERE id_tess=? AND pwd=?;");
                                stmt.setString(1, id_tess);
                                stmt.setString(2, pwd);

                                rs = stmt.executeQuery();

                                if (rs.isBeforeFirst())//controllo che nel resultset sia contenuto qualcosa
                                {
                                    try
                                    {
                                        jsonObject.put("response_code", 201);
                                    } catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);

                                } else
                                {
                                    try
                                    {
                                        jsonObject.put("response_code", 400);
                                    } catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }


                                response.getWriter().println(jsonArray);
                                stmt.close();
                                conn.close();
                            }catch (SQLException e){e.printStackTrace();}
                        }
                        break;

                    case "/nol"://ritorna tutti i noleggi di un utente dato l'id della sua tessera
                        try
                        {
                            PreparedStatement stmt = conn.prepareStatement("SELECT tag_fk, data_start, data_end FROM noleggi WHERE id_tess_fk=?;");
                            stmt.setString(1,id_tess);
                            ResultSet rset = stmt.executeQuery();

                            try
                            {
                                while (rset.next())
                                {
                                    ResultSetMetaData metaData = rset.getMetaData();//ottengo nomi colonne tabella
                                    count = metaData.getColumnCount();//conto le colonne
                                    JSONObject jsonObject = new JSONObject();
                                    for (int a = 1; a<= count; a++)//CREO L'OGGETTO (un singolo utente)
                                    {
                                        try
                                        {
                                            jsonObject.put(metaData.getColumnName(a), rset.getObject(a));//inserisco key e value nel jsonobject
                                        } catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }

                                    jsonArray.put(jsonObject);


                                }//end while
                            }finally
                            {
                                rset.close();
                            }

                            response.getWriter().println(jsonArray);//stampo
                            stmt.close();
                            conn.close();
                        }catch (SQLException e){e.printStackTrace();}
                        break;

                    case "/staz":
                        /* DISP/NDISP
                         * 1/1 stazione piena a metà
                         * 1/0 stazione vuota
                         * false/true stazione piena
                         * false/false impossibile (chiusa?) */
                        try
                        {
                            PreparedStatement stmt = conn.prepareStatement("SELECT disp,ndisp FROM stazioni where id_staz=?");
                            stmt.setString(1,id_staz);
                            ResultSet rset = stmt.executeQuery();

                            try
                            {
                                while (rset.next())
                                {
                                    ResultSetMetaData metaData = rset.getMetaData();//ottengo nomi colonne tabella
                                    count = metaData.getColumnCount();//conto le colonne
                                    JSONObject jsonObject = new JSONObject();
                                    for (int a = 1; a<= count; a++)//CREO L'OGGETTO (un singolo utente)
                                    {
                                        try
                                        {
                                            jsonObject.put(metaData.getColumnName(a), rset.getObject(a));//inserisco key e value nel jsonobject
                                        } catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }

                                    jsonArray.put(jsonObject);


                                }//end while
                            }finally
                            {
                                rset.close();
                            }

                            response.getWriter().println(jsonArray);//stampo
                            stmt.close();
                            conn.close();
                        }catch (SQLException e){e.printStackTrace();}
                        break;




            }
        } catch (SQLException e) {
            response.getWriter().println("Errore SQL");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Errore IO");
        }

    }


    @SuppressWarnings("Duplicates")
    protected void doPut(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


        String new_tag = "";
        String new_stato = "";
        String new_nome="";
        String new_cogn="";
        String new_ind="";
        String new_mail="";
        String new_pwd="";
        Boolean new_disp=false;
        Boolean new_ndisp=false;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        response.setContentType("application/json");

        try {

            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setServerName("localhost");
            dataSource.setDatabaseName("bike_db");
            dataSource.setUser("root");
            dataSource.setPassword("");
            PreparedStatement stmt;

            Connection conn;

            conn = dataSource.getConnection();


            Enumeration<String> parameterNames = request.getParameterNames();//SET DEI POSSIBILI VALORI DELLA REQUEST (nel nostro caso: isbn - titolo - autore - casaed...)

            while (parameterNames.hasMoreElements()) {

                String paramName = parameterNames.nextElement();
                String paramValue = request.getParameter(paramName);//ottengo il valore del parametro che ha nome *paramName*

                switch(paramName) {

                    //aggiorna la tupla:
                    case "tag":
                        if(paramValue!= null) {
                            tag = paramValue;
                        }break;
                    case "id_tess"://non è per l'utente, che invece potra solo modificare i propri dati personali
                        if(paramValue!= null) {
                            id_tess = paramValue;
                        }break;
                    case "id_staz":
                        if(paramValue!= null) {
                            id_staz = paramValue;
                        }break;


                    //nuovi valori
                    case "data_end":
                        if(paramValue!= null) {
                            data_end = paramValue;
                        }break;
                    case "new_tag":
                        if(paramValue!= null) {
                           new_tag = paramValue;
                        }break;
                    case "new_stato":
                        if(paramValue!= null) {
                           new_stato = paramValue;
                        }break;
                    case "new_nome":
                        if(paramValue!= null) {
                           new_nome = paramValue;
                        }break;
                    case "new_cogn":
                        if(paramValue!= null) {
                            new_cogn = paramValue;
                        }break;
                    case "new_ind":
                        if(paramValue!= null) {
                            new_ind = paramValue;
                        }break;
                    case "new_mail":
                        if(paramValue!= null) {
                            new_mail = paramValue;
                        }break;
                    case "new_pwd": //...HASHING & SALTING...
                        if(paramValue!= null) {
                            new_pwd = paramValue;
                        }break;
                    case "new_disp":
                        if(paramValue!= null) {
                            new_disp = Boolean.parseBoolean(paramValue);
                        }break;
                    case "new_ndisp":
                        if(paramValue!= null) {
                            new_ndisp = Boolean.parseBoolean(paramValue);
                        }break;



                }


            }



            switch (request.getRequestURI())
            {
                case "/bike":
                    stmt = conn.prepareStatement("UPDATE `bici` SET `tag`=?,`stato`=? WHERE `tag`=?;");

                    if(new_tag!="")//come fare???
                    {
                        stmt.setString(1, new_tag);
                    }
                    stmt.setString(2,new_stato);
                    stmt.setString(3,tag);

                    int value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);
                    
                    stmt.close();
                    conn.close();

                    break;

                case "/utente":

                    stmt = conn.prepareStatement("UPDATE `utenti` SET `nome`=?,`cogn`=?,`ind`=?,`mail`=?,`pwd`=? WHERE `id_tess`=?;");

                    stmt.setString(1,new_nome);
                    stmt.setString(2,new_cogn);
                    stmt.setString(3,new_ind);
                    stmt.setString(4,new_mail);
                    stmt.setString(5,new_pwd);
                    stmt.setString(6,id_tess);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);

                    stmt.close();
                    conn.close();
                    break;

                case "/staz":
                    stmt = conn.prepareStatement("UPDATE `stazioni` SET `disp`=?,`ndisp`=? WHERE `id_staz`=?;");

                    stmt.setBoolean(1,new_disp);
                    stmt.setBoolean(2,new_ndisp);
                    stmt.setString(3,id_staz);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);

                    stmt.close();
                    conn.close();

                    break;

                case "/nol":
                    java.util.Date data_start;
                    int sec_used=0;
                    try {//trasformo date_end da stringa a timestamp
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//specifico il formato
                        Date parsedDate = dateFormat.parse(data_end);//trasformo data_end in quel formato
                        Timestamp data_end_ts = new java.sql.Timestamp(parsedDate.getTime());//trasformo la data, in quel formato, in timestamp

                        //ottengo la data di inizio di QUEL noleggio
                        stmt = conn.prepareStatement("SELECT data_start FROM noleggi WHERE id_tess_fk=? AND tag_fk=? AND data_end IS NULL ;");
                        stmt.setString(1,id_tess);
                        stmt.setString(2,tag);

                        //recupero la data dal result set
                    ResultSet rs= stmt.executeQuery();
                    while(rs.next())
                    {
                        data_start = rs.getTimestamp("data_start");//data_start !

                        response.getWriter().println(data_start);//debug

                        //calcolo i secondi consumati
                        long diff_ts = data_end_ts.getTime() - data_start.getTime();//differenza tra timestamps in millisecondi
                        sec_used = (int) diff_ts / 1000;//trasformo in secondi (secondi usati)

                        response.getWriter().println(sec_used);//debug
                    }

                        //ottengo i secondi di credito dell'utente
                        stmt = conn.prepareStatement("SELECT sec_cred FROM utenti WHERE id_tess=?;");
                        stmt.setString(1, id_tess);

                        rs = stmt.executeQuery();

                        while(rs.next())
                        {
                            sec_cred = rs.getInt("sec_cred");//recupero sec_cred dell'utente NOTA: RECUPERA "0" !!!!!
                        }

                        //sottraggo i secondi consumati da quelli residui dell'utente
                        sec_cred -= sec_used;

                        response.getWriter().println(sec_cred);//debug

                        //aggiorno i secondi di credito dell'utente
                        stmt = conn.prepareStatement("UPDATE `utenti` SET `sec_cred`=? WHERE `id_tess`=?;");
                        stmt.setInt(1, sec_cred);
                        stmt.setString(2, id_tess);
                        stmt.executeUpdate();

                        //segnalo dove è stata lasciata parcheggiata la bicicletta
                        stmt = conn.prepareStatement("INSERT INTO `parcheggi`(`tag_fk`, `id_staz_fk`) VALUES (?,?)");
                        stmt.setString(1,tag);
                        stmt.setString(2,id_staz);
                        stmt.executeUpdate();

                        //aggiorno la tupla del noleggio in questione
                        stmt = conn.prepareStatement("UPDATE noleggi SET data_end=? WHERE id_tess_fk=? AND tag_fk=? AND data_end IS NULL");
                        stmt.setTimestamp(1, data_end_ts);// FORMATO CORRETTO e.g. 'yyyy-MM-dd 14:29:36'
                        stmt.setString(2, id_tess);
                        stmt.setString(3, tag);
                        //stmt.setNull(4,Types.NULL);
                        value = stmt.executeUpdate();

                        if (value != 0)
                        {
                            jsonObject.put("response_code", 201);
                            jsonArray.put(jsonObject);

                        } else
                        {
                            jsonObject.put("response_code", 400);
                            jsonArray.put(jsonObject);
                        }

                        response.getWriter().println(jsonArray);

                        stmt.close();

                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                    conn.close();
                    break;
                    
            }



        } catch (SQLException | ParseException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    protected void doDelete(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String tag="";
        String id_tess="";
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PreparedStatement stmt;

        try {

            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setServerName("localhost");
            dataSource.setDatabaseName("bike_db");
            dataSource.setUser("root");
            dataSource.setPassword("");

            Connection conn;
            conn = dataSource.getConnection();
            response.setContentType("application/json");

            Enumeration<String> parameterNames = request.getParameterNames();//SET DEI POSSIBILI VALORI DELLA REQUEST (nel nostro caso: isbn - titolo - autore - casaed...)
            String paramName;
            String paramValue;

            while(parameterNames.hasMoreElements()) {

                paramName = parameterNames.nextElement();
                paramValue = request.getParameter(paramName);//ottengo il valore del parametro che ha nome *paramName*

                switch(paramName)
                {
                    case "tag":
                        if (paramValue != null)
                        {
                            tag = paramValue;
                        }
                        break;
                    case "id_tess":
                        if (paramValue != null)
                        {
                            id_tess = paramValue;
                        }
                        break;
                }
        }

            //debug
            //response.getWriter().println(tag); response.getWriter().println(id_tess);

            switch (request.getRequestURI())
            {
                case "/bike":
                    //non sarà possibile cancellare una bici che è impegnata in un noleggio o un parcheggio (foreign key constraint)
                    stmt = conn.prepareStatement("DELETE FROM bici WHERE tag=?;");
                    stmt.setString(1,tag);

                    int value = stmt.executeUpdate();

                    //funziona ma non esce il response code
                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);

                    stmt.close();
                    conn.close();
                    break;

                case "/utente":

                    stmt = conn.prepareStatement("DELETE FROM utenti WHERE id_tess=?;");
                    stmt.setString(1,id_tess);

                    value = stmt.executeUpdate();

                    //funziona ma non ritorna il response code!
                    if(value!=0)
                    {
                        jsonObject.put("response_code",201);
                        jsonArray.put(jsonObject);

                    }else
                    {
                        jsonObject.put("response_code",400);
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().println(jsonArray);
                    stmt.close();
                    conn.close();
                    break;

            }


        } catch (SQLException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
