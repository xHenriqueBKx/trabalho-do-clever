import com.connection.Sender;

public class Connection {
    public static void main(String[] args) {
        String table = "User";
        String order = "Read";
        String cpf = "00000000000";
        Sender.send(table, order, cpf);
    }
}