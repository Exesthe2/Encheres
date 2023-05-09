package bll;

import bo.Users;
import dal.AuthDAO;
import dal.DAOFactory;

import java.sql.SQLException;
import java.util.Objects;
import java.util.List;

public class UserBLL {

    private AuthDAO dao;

    public UserBLL() {
        dao = DAOFactory.getAuthDAO();
    }

    public Users loginBLL(String emailOrPseudo, String password) throws BLLException {
        return dao.login(emailOrPseudo, password);
    }
    public Users SelectById(int id) throws BLLException { return dao.SelectById(id); }

    public void DeleteUser(Users user, String password) throws  BLLException {
        int id = user.getNo_utilisateur();
        if (VerifPassword(id, password)) {
            dao.DeleteUser(id);
        }
    }

    public void Update(Users user, String password) throws SQLException, BLLException  {
        if (DataNotNull(user)) {
            if (VerifPassword(user.getNo_utilisateur(), password)) {
                dao.UpdateProfile(user);
            }
        }
    }

    public void UpdateWithNewPassWord(Users user,String password, String newpassword, String confirmation) throws BLLException {
        if(DataNotNull(user)){
            if(VerifPassword(user.getNo_utilisateur(), password))
                if(NewPassword(newpassword, confirmation)){
                    user.setMot_de_passe(newpassword);
                    dao.UpdateProfileWithPassWord(user);
                }
        }
    }

    public boolean VerifPassword(int id, String password) throws BLLException {   // verifie que le mot de passe saisie est le bon suivant l'id
        String pass = String.valueOf(dao.VerifPassword(id));
        boolean acces = false;
        if(password.equals(pass)){
            acces = true;
        }else{
            throw new BLLException("Le mot de passe actuel n'est pas bon");
        }
        return acces;
    }

    public boolean DataNotNull(Users user)throws BLLException{ // Verifie que les données ne sont pas null
        boolean acces = true;
        if (user.getNom().isEmpty() || user.getPrenom().isBlank() || user.getEmail().isBlank() || user.getTelephone().isBlank() || user.getRue().isBlank() || user.getCode_postal().isBlank() || user.getVille().isBlank()){
                acces = false;
                throw new BLLException("Tous les champs doivent être rempli");
        }

        return acces;
    }

    public boolean NewPassword(String password, String confirmation) throws BLLException {  // Verifie que le nouveau mot de passe est le même que dans le champs confirmation
        boolean acces = false;
        if (password.equals(confirmation)) {
            acces = true;
        } else {
            throw new BLLException("Le nouveau mot de passe et sa confirmation ne sont pas bon");
        }
        return acces;
    }

    public List<String> pseudosAndEmailsBLL() throws BLLException {
        return dao.pseudosAndEmails();
    }

    public Users newUserBLL(String pseudo, String firstname, String lastname, String phoneNumber, String postalCode, String street, String town, String email, String password, String confirmPassword) throws BLLException {
        return dao.registerUser(pseudo, firstname, lastname, phoneNumber, postalCode, street, town, email, password, confirmPassword);
    }

    public boolean verifSomme(int no_utilisateur, int montant) throws BLLException, SQLException {
        boolean acces = false;
        int portefeuille;
        portefeuille = dao.selectPortefeuille(no_utilisateur);
        if(portefeuille>=montant){
            acces = true;
        }else{
            throw new BLLException("Le montant de votre portefeuille est insuffisant");
        }
        return acces;
    }
}
