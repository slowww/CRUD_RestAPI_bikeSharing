import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mariadb.jdbc.MariaDbDataSource;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

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
    String data_start="";
    Date data_end=null;
    int value;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

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
                    case "data_start":
                        if (paramValue != null) {
                            data_start = paramValue;
                        }
                        break;
                    case "data_end":
                        if (paramValue != null) {
                            data_end = (Date) new SimpleDateFormat("dd/MM/yy").parse(paramValue);;
                        }
                        break;
                }


            }

           // query += "(" + "'" + isbn + "'" + "," + "'" + titolo + "'" + "," + "'" + autore + "'" + "," + "'" + casaed + "'" + ");";

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
                        response.setStatus(201);//in json
                    }else
                    {
                        response.setStatus(400);
                    }


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
                        response.setStatus(201);
                    }else
                    {
                        response.setStatus(400);
                    }


                    stmt.close();
                    conn.close();
                    break;
                case "/staz":
                    stmt = conn.prepareStatement("INSERT INTO `stazioni`(`id_staz`, `disp`, `ndisp`,`ind_staz`) VALUES (?,?,?,?);");
                    stmt.setString(1,id_staz);
                    stmt.setBoolean(2,disp);
                    stmt.setBoolean(3,ndisp);
                    stmt.setString(4,ind_staz);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        response.setStatus(200);//in json
                    }else
                    {
                        response.setStatus(400);
                    }


                    stmt.close();
                    conn.close();
                    break;
                case "/nol":
                    stmt = conn.prepareStatement("INSERT INTO `noleggi`(`id_tess`, `data_start`, `data_end`,`tag`) VALUES (?,?,?,?);");
                    stmt.setString(1,id_tess);
                    stmt.setString(2,data_start);
                    stmt.setNull(3, Types.NULL);//OK?
                    stmt.setString(4,tag);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        response.setStatus(200);//in json
                    }else
                    {
                        response.setStatus(400);
                    }

                    stmt.close();
                    conn.close();
                    break;
                case "/park":
                    stmt = conn.prepareStatement("INSERT INTO `parcheggi`(`tag`,`data_park`,`id_staz`) VALUES (?,?,?);");
                    stmt.setString(1,tag);
                    stmt.setNull(2, Types.NULL);//OK? sulla tabella verrà valorizzato il campo con un timestamp automatico (settare TIMESTAMP - CURRENT TIMESTAMP)
                    stmt.setString(3,id_staz);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        response.setStatus(200);//in json

                    }else
                    {
                        response.setStatus(400);
                    }


                    stmt.close();
                    conn.close();
                    break;
            }




            /*stmt = conn.prepareStatement("DELETE FROM libri WHERE isbn=?;");
            stmt.setString(1,isbn);*/




            //response.getWriter().println(query); DEBUG







        } catch (SQLException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }





    }

    @SuppressWarnings("Duplicates")
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    //PER AUTENTICAZIONE LOGIN
        int count;
        int i;

        try {

            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setServerName("localhost");
            dataSource.setDatabaseName("bike_db");
            dataSource.setUser("root");
            dataSource.setPassword("");

            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM utenti");
            /*A ResultSet is a Java object that contains the results of executing an SQL query. In other words, it contains the rows that satisfy the conditions of the query.
             * The data stored in a ResultSet object is retrieved through a set of get methods that allows access to the various columns of the current row.*/

            response.setContentType("application/json");


            JSONArray jsonArray = new JSONArray();


            while (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();//ottengo nomi colonne tabella

                count = metaData.getColumnCount();//conto le colonne

                JSONObject jsonObject = new JSONObject();//creo oggetto per contenere il json
                for (i = 1; i <= count; i++) {

                    try {
                        jsonObject.put(metaData.getColumnName(i), rs.getObject(i));//inserisco key e value nel jsonobject
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jsonObject.put("Status", response.SC_OK); //OK?????????
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);//metto il jsonobject in un apposito array
            }


            response.getWriter().println(jsonArray);//stampo


            stmt.close();
            conn.close();
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

            //RESULT SET SULLA CONNECTION E GET DATE SU RESULTSET PER RECUPERARE DATA_END DALLA "BICICLETTA" CHE TERMINA IL NOLEGGIO

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
                    case "data_end":
                        if(paramValue!= null) {
                            data_end = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(paramValue);
                        }break;
                    //nuovi valori
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
                    case "new_pwd": //HASHING & SALTING
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

                    stmt.setString(1,new_tag);
                    stmt.setString(2,new_stato);
                    stmt.setString(3,tag);

                    int value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        response.setStatus(200);
                    }else
                    {
                        response.setStatus(400);
                    }
                    
                    stmt.close();
                    conn.close();

                    break;

                case "/utente":

                    stmt = conn.prepareStatement("UPDATE `utenti` SET `nome`=?,`cogn`=?`ind`=?,`mail`=?`pwd`=? WHERE `id_tess`=?;");

                    stmt.setString(1,new_nome);
                    stmt.setString(2,new_cogn);
                    stmt.setString(3,new_ind);
                    stmt.setString(4,new_mail);
                    stmt.setString(5,new_pwd);
                    stmt.setString(6,id_tess);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        response.setStatus(200);
                    }else
                    {
                        response.setStatus(400);
                    }

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
                        response.setStatus(200);
                    }else
                    {
                        response.setStatus(400);
                    }

                    stmt.close();
                    conn.close();

                    break;

                case "/nol":
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                    java.util.Date ds=null;
                    java.util.Date de=null;

                    int sec_cred = 0;
                    //int sec_cred = data_end - data_start [in secondi]
                    try {
                        ds = format.parse(data_start);
                        de = (java.util.Date) format.parse(String.valueOf(data_end));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    sec_cred = Math.toIntExact((ds.getTime() - de.getTime()) / 1000);


                    stmt = conn.prepareStatement("UPDATE `noleggi` SET `sec_cred`=?,`data_end`=? WHERE `id_tess_fk`=?;");

                    stmt.setInt(1,sec_cred);
                    stmt.setString(2, String.valueOf(data_end));
                    stmt.setString(3,id_tess);

                    value = stmt.executeUpdate();

                    if(value!=0)
                    {
                        response.setStatus(200);
                    }else
                    {
                        response.setStatus(400);
                    }

                    stmt.close();
                    conn.close();
                    break;
                    
            }



        } catch (SQLException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    protected void doDelete(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String tag="";

        try {

            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setServerName("localhost");
            dataSource.setDatabaseName("bike_db");
            dataSource.setUser("root");
            dataSource.setPassword("");
            PreparedStatement stmt;
            Connection conn;
            conn = dataSource.getConnection();
            response.setContentType("application/json");

            Enumeration<String> parameterNames = request.getParameterNames();//SET DEI POSSIBILI VALORI DELLA REQUEST (nel nostro caso: isbn - titolo - autore - casaed...)
            String paramName;
            String paramValue;

            //while (parameterNames.hasMoreElements()) {

            paramName = parameterNames.nextElement();

            paramValue = request.getParameter(paramName);//ottengo il valore del parametro che ha nome *paramName*

            if (paramName.equalsIgnoreCase("tag") && paramValue != null) {
                //cancella la tupla con il seguente tag:
                tag = paramValue;
            }else if (paramName.equalsIgnoreCase("id_tess") && paramValue != null)
            {
                id_tess = paramValue;
            }

        //} WHILE BRACE

            //debug
            response.getWriter().println(tag); response.getWriter().println(id_tess);

            switch (request.getRequestURI())
            {
                case "/bike":
                    stmt = conn.prepareStatement("DELETE FROM bici WHERE tag=?;");
                    stmt.setString(1,tag);

                    int righe_cancellate = stmt.executeUpdate();

                    if(righe_cancellate!=0)
                    {
                        response.getWriter().println("Cancellazione avvenuta correttamente. Righe cancellate: " + righe_cancellate);
                        //JSON RESPONSE CODE

                    }else
                    {
                        response.getWriter().println("Cancellazione non avvenuta");
                        //JSON RESPONSE CODE
                    }

                    stmt.close();
                    conn.close();
                    break;

                case "/utente":
                    stmt = conn.prepareStatement("DELETE FROM utenti WHERE id_tess=?;");
                    stmt.setString(1,id_tess);

                    righe_cancellate = stmt.executeUpdate();

                    if(righe_cancellate!=0)
                    {
                        response.getWriter().println("Cancellazione avvenuta correttamente. Righe cancellate: " + righe_cancellate);
                        //JSON RESPONSE CODE

                    }else
                    {
                        response.getWriter().println("Cancellazione non avvenuta");
                        //JSON RESPONSE CODE
                    }

                    stmt.close();
                    conn.close();
                    break;

            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
