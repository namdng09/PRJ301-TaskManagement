package dao;

import java.sql.*;
import model.Card;

public class CardDAO {

    public void insertCard(Card card, String listTaskID) throws SQLException {
        String query = "INSERT INTO [dbo].[Card]"
                + "([CardID],[ListID],[Name],[Description],[CreatedDate])"
                + "VALUES (?,?,?,?,?)";
        PreparedStatement pstmt = createPreparedStatement(query);
        pstmt.setString(1, card.getCardID());
        pstmt.setString(2, listTaskID);
        pstmt.setString(3, card.getCardName());
        pstmt.setString(4, card.getDescription());
        pstmt.setDate(5, card.getCreatedDate());

        pstmt.executeUpdate();
    }

    public ResultSet getAllCardByListTaskID(String listTaskID) throws SQLException {
        String query = "SELECT * FROM [dbo].[Card] WHERE [ListTaskID] = ?";
        PreparedStatement pstmt = createPreparedStatement(query);
        // modify the query here
        pstmt.setString(1, listTaskID);
        return executeQuery(pstmt);
    }

    public ResultSet getLastestCardID() throws SQLException {
        String query = "SELECT [CardID] FROM [dbo].[Card] ORDER BY [CardID] DESC LIMIT 1";
        PreparedStatement pstmt = createPreparedStatement(query);
        // modify the query here
        return executeQuery(pstmt);
    }

    public void deleteCard(String cardID) throws SQLException {
        String query = "DELETE FROM [dbo].[Card] WHERE [CardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        // modify the query here
        pstmt.setString(1, cardID);

        pstmt.executeUpdate();
    }

    public void updateCardName(String cardID, String name) throws SQLException {
        String query = "UPDATE [dbo].[Card] SET [Name] = ? WHERE [CardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        // modify the query here
        pstmt.setString(1, name);
        pstmt.setString(2, cardID);

        pstmt.executeUpdate();
    }

    public void updateCardDescription(String cardID, String description) throws SQLException{
        String query = "UPDATE [dbo].[Card] SET [Description] = ? WHERE [CardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        // modify the query here
        pstmt.setString(1, description);
        pstmt.setString(2, cardID);

        pstmt.executeUpdate();
    }

    public void updateCardDueDate(String cardID, Date dueDate) throws SQLException{
        String query = "UPDATE [dbo].[Card] SET [DueDate] = ? WHERE [CardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        // modify the query here
        pstmt.setDate(1, dueDate);
        pstmt.setString(2, cardID);

        pstmt.executeUpdate();
    }
    /**
     * Create instance for PreparedStatement class
     *
     * @param query the SQL query to be prepared.
     *
     * @return a PreparedStatement object that can be used to execute the query
     * with specified parameters.
     *
     * @throws SQLException if a database access error occurs or the SQL
     * statement cannot be pre-compiled.
     */
    public PreparedStatement createPreparedStatement(String query) throws SQLException {
        Connection conn = DBContext.getInstance().getConnection();
        return conn.prepareStatement(query);
    }

    /**
     * Executes a SQL query using the provided PreparedStatement.
     *
     * @param pstmt the PreparedStatement to be executed.
     * @return the ResultSet obtained from executing the query.
     * @throws java.sql.SQLException
     */
    public ResultSet executeQuery(PreparedStatement pstmt) throws SQLException {
        return pstmt.executeQuery();
    }
}
