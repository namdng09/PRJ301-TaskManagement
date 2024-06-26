package dao;

import java.sql.*;
import model.Board;

public class BoardDAO {

    public void insertBoard(Board board, String useruid) throws SQLException {
        String query = "INSERT INTO [dbo].[Board]"
                + "([BoardID],[User_UID],[Name],[CreatedDate],[isPublic],[isCompleted], [description],[Image])"
                + "VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = createPreparedStatement(query);
        pstmt.setString(1, board.getBoardID());
        pstmt.setString(2, useruid);
        pstmt.setString(3, board.getBoardName());
        pstmt.setDate(4, board.getCreateDate());
        pstmt.setBoolean(5, board.isPubliced());
        pstmt.setBoolean(6, board.isCompleted());
        pstmt.setString(7, board.getDescription());
        pstmt.setString(8, board.getImage());

        pstmt.executeUpdate();
    }

    public void deleteBoard(String boardID) throws SQLException {
        String query = "DELETE FROM [dbo].[Board] WHERE [BoardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        pstmt.setString(1, boardID);

        pstmt.executeUpdate();
    }

    public void updateBoardName(String boardID, String newName) throws SQLException {
        String query = "UPDATE [dbo].[Board] SET [Name] = ? WHERE [BoardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        pstmt.setString(1, newName);
        pstmt.setString(2, boardID);

        pstmt.executeUpdate();
    }

    public void deleteBoardMember(String useruid, String boardID) throws SQLException {
        String query = "DELETE FROM [dbo].[BoardMember]"
                + "WHERE [User_UID] = ?"
                + "AND [BoardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        pstmt.setString(1, useruid);
        pstmt.setString(2, boardID);

        pstmt.executeUpdate();
    }

    public void insertBoardMember(String useruid, String boardID) throws SQLException {
        String query = "INSERT INTO [dbo].[BoardMember]"
                + "([User_UID], [BoardID])"
                + "VALUES (?,?)";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        pstmt.setString(1, useruid);
        pstmt.setString(2, boardID);

        pstmt.executeUpdate();
    }

    public ResultSet getBoardByBoardID(String boardID) throws SQLException {
        String query = "SELECT [Board].[BoardID], [Board].[Name], "
                + "CONCAT([User].[Firstname], ' ', [User].[Lastname]) AS FullName, "
                + "[Board].[CreatedDate], [Board].[isPublic], [Board].[isCompleted], "
                + "[Board].[Description], [Board].[Image] "
                + "FROM [dbo].[Board] "
                + "JOIN [dbo].[BoardMember] ON [Board].[BoardID] = [BoardMember].[BoardID] "
                + "JOIN [dbo].[User] ON [BoardMember].[User_UID] = [User].[User_UID] "
                + "WHERE [Board].[BoardID] = ?";
        PreparedStatement pstmt = createPreparedStatement(query);
        pstmt.setString(1, boardID);
        return executeQuery(pstmt);
    }

    public ResultSet getAllBoardByName(String useruid, String partOfName) throws SQLException {
        String query = "SELECT [Board].[BoardID], [Board].[Name], "
                + "CONCAT([User].[Firstname], ' ', [User].[Lastname]) AS FullName, "
                + "[Board].[CreatedDate], [Board].[isPublic], [Board].[isCompleted], "
                + "[Board].[Description], [Board].[Image] "
                + "FROM [dbo].[Board] "
                + "JOIN [dbo].[BoardMember] ON [Board].[BoardID] = [BoardMember].[BoardID] "
                + "JOIN [dbo].[User] ON [BoardMember].[User_UID] = [User].[User_UID] "
                + "WHERE [BoardMember].[User_UID] = ? AND [Board].[Name] LIKE ?";
        PreparedStatement pstmt = createPreparedStatement(query);
        pstmt.setString(1, useruid);
        pstmt.setString(2, "%" + partOfName + "%");
        return executeQuery(pstmt);
    }

    public ResultSet getAllBoardByUserUID(String useruid) throws SQLException {
        String query = "SELECT [Board].[BoardID], [Board].[Name], "
                + "CONCAT([User].[Firstname], ' ', [User].[Lastname]) AS FullName, "
                + "[Board].[CreatedDate], [Board].[isPublic], [Board].[isCompleted], "
                + "[Board].[Description], [Board].[Image] "
                + "FROM [dbo].[Board] "
                + "JOIN [dbo].[BoardMember] ON [Board].[BoardID] = [BoardMember].[BoardID] "
                + "JOIN [dbo].[User] ON [BoardMember].[User_UID] = [User].[User_UID] "
                + "WHERE [BoardMember].[User_UID] = ?";
        PreparedStatement pstmt = createPreparedStatement(query);
        pstmt.setString(1, useruid);
        return executeQuery(pstmt);
    }

    public ResultSet getAllUserByBoardID(String boardID) throws SQLException {
        String query = "SELECT [User].[User_UID], [User].[Username], [User].[Email], "
                + "[User].[FirstName], [User].[LastName] "
                + "FROM [dbo].[User] "
                + "JOIN [dbo].[BoardMember] ON [User].[User_UID] = [BoardMember].[User_UID] "
                + "WHERE [BoardMember].[BoardID] = ?";
        PreparedStatement pstmt = createPreparedStatement(query);
        pstmt.setString(1, boardID);
        return executeQuery(pstmt);
    }

    public void toggleIsPublic(String boardID) throws SQLException {
        String query = "UPDATE [dbo].[Board] SET [isPublic] = NOT [isPublic] WHERE [BoardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        pstmt.setString(1, boardID);
        pstmt.executeUpdate();
    }

    public ResultSet getLastestBoardID() throws SQLException {
        String query = "SELECT [BoardID] FROM [dbo].[Board] ORDER BY [BoardID] DESC LIMIT 1";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        // modify the query here
        return executeQuery(pstmt);
    }

    public ResultSet countMemberBoard(String useruid, String boardID) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM [dbo].[BoardMember] WHERE [User_UID] = ? AND [BoardID] = ?";
        PreparedStatement pstmt = this.createPreparedStatement(query);
        // modify the query here
        pstmt.setString(1, useruid);
        pstmt.setString(2, boardID);
        return executeQuery(pstmt);
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
