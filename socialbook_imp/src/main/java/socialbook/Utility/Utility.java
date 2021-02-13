package socialbook.utility;

import socialbook.model.GestioneDatabase.Review;
import socialbook.model.GestioneDatabase.ReviewDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static String aggiuntaFoto(HttpServletRequest request) throws IOException, ServletException {

        String CARTELLA_UPLOAD = "images";  //cartella in cui verranno salvate le immagini prese dal form

        Part filePart = request.getPart("foto");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String destinazione = CARTELLA_UPLOAD + File.separator + fileName;
        Path pathDestinazione = Paths.get(request.getServletContext().getRealPath(destinazione));

        // se un file con quel nome esiste già, gli cambia nome
        for (int i = 2; Files.exists(pathDestinazione); i++) {
            destinazione = CARTELLA_UPLOAD + File.separator + i + "_" + fileName;
            pathDestinazione = Paths.get(request.getServletContext().getRealPath(destinazione));
        }

        InputStream fileInputStream = filePart.getInputStream();
        // crea CARTELLA_UPLOAD, se non esiste
        Files.createDirectories(pathDestinazione.getParent());
        // scrive il file
        Files.copy(fileInputStream, pathDestinazione);

        return fileName;
    }

    public static String encryptionSHA1(String pwd){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(pwd.getBytes(StandardCharsets.UTF_8));
            pwd = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return pwd;
    }

    public static void redirect(HttpServletResponse response, String destinazione, String confronto) throws IOException {
        if (destinazione == null || destinazione.contains(confronto) || destinazione.trim().isEmpty()) {
            destinazione = ".";     //la destinazione sarà l'homepage
        }
        response.sendRedirect(destinazione);
    }

    public static String formatDate(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return date == null ? "" : sdf.format(date);
    }

    public static void checkReview(HttpServletRequest request, String isbn, int id_customer) {
        ReviewDAO reviewDAO = new ReviewDAO();
        Review review = reviewDAO.doRetrieveByISBNCustomer(isbn, id_customer);

        if(review == null)      //utente non ha mai recensito questo libro
            request.setAttribute("recensione_si", "si");
        else {
            String voto = review.getVote();
            String body = review.getBody();

            if (!voto.equals("-") && !body.equals("-"))
                request.setAttribute("recensione_no", "no");   //utente ha inserito sia voto che commento

            if (voto.equals("-"))
                request.setAttribute("vote", "si");     //utente ha inserito solo il commento

            if (body.equals("-"))
                request.setAttribute("body", "si");     //utente ha inserito solo il voto
        }
    }
}
