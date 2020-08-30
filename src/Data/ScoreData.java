/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import ConnectService.sqlConnect;
import Model.Result;
import Model.Score;
import Model.Student;
import Model.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 84969
 */
public class ScoreData {

    private ArrayList<Student> listStudents;
    private ArrayList<Score> listScores;
    private ArrayList<Subject> listSubjects;
    private ArrayList<Result> listGpa;
    private Subject subjectSelected;
    private Score scoreSelected;
    private Connection connection;
    private int student_id;

    public ScoreData() {
        try {
            this.connection = sqlConnect.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentData.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listStudents = new ArrayList<>();
        this.listScores = new ArrayList<>();
        this.listSubjects = new ArrayList<>();
    }

    public ArrayList<Student> getListStudents() {
        return this.listStudents;
    }

    public ArrayList<Score> getListScores() {
        return this.listScores;
    }

    public ArrayList<Subject> getListSubjects() {
        return this.listSubjects;
    }

    public ArrayList<Result> getListGpa() {
        return this.listGpa;
    }

    public void setSubjectSelected(Subject subject) {
        this.subjectSelected = subject;
    }

    public void setScoreSelected(Score scoreSelected) {
        this.scoreSelected = scoreSelected;
    }

    public void setStudentSelected(int id) {
        this.student_id = id;
    }

    public void getData() {
        try {
            this.listStudents = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbo.Students");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String mssv = resultSet.getString("mssv");
                String sex = resultSet.getString("sex");
                String address = resultSet.getString("address");
                float cpa = resultSet.getFloat("cpa");
                String hocluc = resultSet.getString("hocluc");
                Student student = new Student(id, name, age, email, phone, mssv, sex, address, cpa, hocluc);
                listStudents.add(student);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataListSubject() {
        this.listSubjects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Subjects");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int tin = resultSet.getInt("tin");
                Float heso = resultSet.getFloat("heso");
                Subject subject = new Subject(id, name, tin, heso);
                listSubjects.add(subject);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListStudentByQuery(String nameQuery, String emailQuery, String mssvQuery, String sexQuery) {
        this.listStudents.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbo.Students WHERE name LIKE N'%" + nameQuery + "%' AND email LIKE N'%" + emailQuery + "%' AND mssv LIKE '%" + mssvQuery + "%' AND sex LIKE N'%" + sexQuery + "%'");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String mssv = resultSet.getString("mssv");
                String sex = resultSet.getString("sex");
                String address = resultSet.getString("address");
                float cpa = resultSet.getFloat("cpa");
                String hocluc = resultSet.getString("hocluc");
                Student student = new Student(id, name, age, email, phone, mssv, sex, address, cpa, hocluc);
                listStudents.add(student);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataGpa(int studentId) {
        try {
            this.listGpa = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from Results WHERE student_id = " + studentId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String hocki = resultSet.getString("hocki");
                float gpa = resultSet.getFloat("gpa");
                String hocluc = resultSet.getString("hocluc");
                int tcQua = resultSet.getInt("tcQua");
                int tcNo = resultSet.getInt("tcNo");
                String status = resultSet.getString("status");
                Result result = new Result(id, studentId, gpa, hocki, hocluc, tcQua, tcNo, status);
                listGpa.add(result);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataScore(int studentId) {
        try {
            this.listScores = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *,Student_Subject_Relations.id AS score_id FROM dbo.Subjects JOIN dbo.Student_Subject_Relations ON Student_Subject_Relations.subject_id = Subjects.id WHERE student_id = " + studentId);
            while (resultSet.next()) {
                int score_id = resultSet.getInt("score_id");
                String name = resultSet.getString("name");
                int tin = resultSet.getInt("tin");
                float heso = resultSet.getFloat("heso");
                int student_id = resultSet.getInt("student_id");
                float mid_score = resultSet.getFloat("mid_score");
                float last_score = resultSet.getFloat("last_score");
                String grades = resultSet.getString("grades");
                String hocki = resultSet.getString("semester");
                Score score = new Score(score_id, name, student_id, mid_score, last_score, hocki, grades, tin, heso);
                listScores.add(score);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editScore(float mid_score, float last_score) {
        float heso = scoreSelected.getHeSo();
        String grades = "";
        float score = mid_score * heso + last_score * (1 - heso);
        if (score > 0.0 && score < 4.0) {
            grades = "F";
        } else if (score >= 4.0 && score < 5.0) {
            grades = "D";
        } else if (score >= 5.0 && score < 5.5) {
            grades = "D+";
        } else if (score >= 5.5 && score < 6.5) {
            grades = "C";
        } else if (score >= 6.5 && score < 6.9) {
            grades = "C+";
        } else if (score >= 7.0 && score < 7.9) {
            grades = "B";
        } else if (score >= 8.0 && score < 8.5) {
            grades = "B+";
        } else if (score >= 8.5 && score < 9.5) {
            grades = "A";
        } else if (score >= 9.5 && score <= 10.0) {
            grades = "A+";
        }
        try {

            PreparedStatement preStatement = connection.prepareStatement("UPDATE Student_Subject_Relations "
                    + "SET mid_score = ?, last_score = ?, grades = ? WHERE id = ?");
            preStatement.setFloat(1, mid_score);
            preStatement.setFloat(2, last_score);
            preStatement.setString(3, grades);
            preStatement.setInt(4, scoreSelected.getId());

            preStatement.executeUpdate();
            preStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // cập nhật gpa
        getGpa(student_id, scoreSelected.getHocKi());
        // cập nhật cpa
        float cpa = getCpa(student_id);
        updateCpa(student_id, cpa);
    }

    public void createScore(int student_id, float mid_score, float last_score, String hocki) {
        int isCreate = 0;
        float heso = subjectSelected.getHeso();
        String grades = "";
        int tcNo = 0;
        float score = mid_score * heso + last_score * (1 - heso);
        if (score > 0.0 && score < 4.0) {
            grades = "F";
            tcNo = subjectSelected.getTin();
        } else if (score >= 4.0 && score < 5.0) {
            grades = "D";
        } else if (score >= 5.0 && score < 5.5) {
            grades = "D+";
        } else if (score >= 5.5 && score < 6.5) {
            grades = "C";
        } else if (score >= 6.5 && score < 6.9) {
            grades = "C+";
        } else if (score >= 7.0 && score < 7.9) {
            grades = "B";
        } else if (score >= 8.0 && score < 8.5) {
            grades = "B+";
        } else if (score >= 8.5 && score < 9.5) {
            grades = "A";
        } else if (score >= 9.5 && score <= 10.0) {
            grades = "A+";
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbo.Student_Subject_Relations where student_id = " + student_id + " and subject_id = " + subjectSelected.getId() +" and semester = "+hocki);
            if (resultSet.next()) {
                isCreate = 1;
            }
            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isCreate == 1) {
            JOptionPane.showMessageDialog(null, "Điểm này đã tồn tại!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                PreparedStatement preStatement = connection.prepareStatement("INSERT INTO Student_Subject_Relations ( student_id, subject_id, mid_score, last_score, grades,semester ) VALUES  (" + "?,?,?,?,?,?)");
                preStatement.setInt(1, student_id);
                preStatement.setInt(2, subjectSelected.getId());
                preStatement.setFloat(3, mid_score);
                preStatement.setFloat(4, last_score);
                preStatement.setString(5, grades);
                preStatement.setString(6, hocki);

                preStatement.executeUpdate();
                preStatement.close();

                Score scoreNew = new Score(subjectSelected.getName(), student_id, mid_score, last_score, hocki, grades, subjectSelected.getTin(), subjectSelected.getHeso());
                listScores.add(scoreNew);
                getGpa(student_id, hocki);
                float cpa = getCpa(student_id);
                updateCpa(student_id, cpa);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteScore(Score score, int student_id) {
        try {

            PreparedStatement preStatement = connection.prepareStatement("DELETE FROM Student_Subject_Relations WHERE id = ?");
            preStatement.setInt(1, score.getId());
            preStatement.executeUpdate();
            preStatement.close();
            listScores.remove(score);
            // cập nhật gpa
            getGpa(student_id, score.getHocKi());
            // cập nhật cpa
            float cpa = getCpa(student_id);
            updateCpa(student_id, cpa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getGpa(int student_id, String hocki) {
        int tin = 0;
        String grades = "";
        float score = 0;
        int Tin = 0;
        int tcQua = 0;
        int tcNo = 0;
        float gpa = 0;
        String status = "";
        float sum = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT grades,tin FROM dbo.Student_Subject_Relations JOIN dbo.Subjects ON Subjects.id = Student_Subject_Relations.subject_id where student_id = " + student_id + " and semester = " + hocki);
            while (resultSet.next()) {
                tin = resultSet.getInt("tin");
                grades = resultSet.getString("grades");
                switch (grades) {
                    case "A": {
                        score = (float) 4.0;
                        break;
                    }
                    case "A+": {
                        score = (float) 4.0;
                        break;
                    }
                    case "B+": {
                        score = (float) 3.5;
                        break;
                    }
                    case "B": {
                        score = (float) 3.0;
                        break;
                    }
                    case "C+": {
                        score = (float) 2.5;
                        break;
                    }
                    case "C": {
                        score = (float) 2.0;
                        break;
                    }
                    case "D": {
                        score = (float) 1.5;
                        break;
                    }
                    case "D+": {
                        score = (float) 1.0;
                        break;
                    }
                    case "F": {
                        score = 0;
                        break;
                    }
                    default: {
                        score = 0;
                    }
                }
                sum = (float) (sum + (float) (score * tin));
                Tin = Tin + tin;
                if (grades.equals("F")) {
                    tcNo = tcNo + tin;
                } else {
                    tcQua = tcQua + tin;
                }
                if (tcNo < 8) {
                    status = "Cảnh cáo mức 0";
                } else if (tcNo >= 8 && tcNo <= 16) {
                    status = "Cảnh cáo mức 1";
                } else if (tcNo > 16 && tcNo <= 27) {
                    status = "Cảnh cáo mức 2";
                } else if (tcNo > 27) {
                    status = "Cảnh cáo mức 3";
                }
            }
            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Tin == 0) {
            gpa = 0;
        } else {
            gpa = (float) (sum / Tin);
            String hocluc = "";
            if (gpa < 1.0) {
                hocluc = "Kém";
            } else if (gpa >= 1.0 && gpa < 1.5) {
                hocluc = "Yếu";
            } else if (gpa >= 1.5 && gpa < 2) {
                hocluc = "TB yếu";
            } else if (gpa >= 2.0 && gpa < 2.5) {
                hocluc = "Trung bình";
            } else if (gpa >= 2.5 && gpa < 3.2) {
                hocluc = "Khá";
            } else if (gpa >= 3.2 && gpa < 3.6) {
                hocluc = "Giỏi";
            } else if (gpa >= 3.6 && gpa <= 4.0) {
                hocluc = "Xuất sắc";
            }
            int isCreate = 0;
             try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Results where student_id = " + student_id + " and hocki = " + hocki);
            if (resultSet.next()) {
                isCreate = 1;
            }
            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
           if(isCreate == 1){
                try {
                PreparedStatement preStatement = connection.prepareStatement("UPDATE Results "
                        + "SET gpa = ?, hocluc = ?,tcQua = ?,tcNo = ?,status = ? WHERE student_id = ? and hocki = ?");
                preStatement.setFloat(1, gpa);
                preStatement.setString(2, hocluc);
                preStatement.setInt(3, tcQua);
                preStatement.setInt(4, tcNo);
                preStatement.setString(5, status);
                preStatement.setInt(6, student_id);
                preStatement.setString(7, hocki);

                preStatement.executeUpdate();
                preStatement.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
           }
           else {
                try {
                PreparedStatement preStatement = connection.prepareStatement("INSERT INTO Results ( student_id, hocki, gpa, hocluc, tcQua,tcNo,status) VALUES  (" + "?,?,?,?,?,?,?)");
                preStatement.setInt(1, student_id);
                preStatement.setString(2,hocki);
                preStatement.setFloat(3, gpa);
                preStatement.setString(4, hocluc);
                preStatement.setInt(5, tcQua);
                preStatement.setInt(6, tcNo);
                preStatement.setString(7, status);

                preStatement.executeUpdate();
                preStatement.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
           }
        }
    }

    public void updateCpa(int student_id, float cpa) {
        String hocluc = "";
        if (cpa < 1.0) {
            hocluc = "Kém";
        } else if (cpa >= 1.0 && cpa < 1.5) {
            hocluc = "Yếu";
        } else if (cpa >= 1.5 && cpa < 2) {
            hocluc = "TB yếu";
        } else if (cpa >= 2.0 && cpa < 2.5) {
            hocluc = "Trung bình";
        } else if (cpa >= 2.5 && cpa < 3.2) {
            hocluc = "Khá";
        } else if (cpa >= 3.2 && cpa < 3.6) {
            hocluc = "Giỏi";
        } else if (cpa >= 3.6 && cpa <= 4.0) {
            hocluc = "Xuất sắc";
        }
        try {
            PreparedStatement preStatement = connection.prepareStatement("UPDATE Students "
                    + "SET cpa = ?,hocluc = ? WHERE id = ?");
            preStatement.setFloat(1, cpa);
            preStatement.setString(2, hocluc);
            preStatement.setInt(3, student_id);

            preStatement.executeUpdate();
            preStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getCpa(int student_id) {
        int tin = 0;
        String grades = "";
        float score = 0;
        int Tin = 0;
        float cpa = 0;
        float sum = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT grades,tin FROM dbo.Student_Subject_Relations JOIN dbo.Subjects ON Subjects.id = Student_Subject_Relations.subject_id where student_id = " + student_id);
            while (resultSet.next()) {
                tin = resultSet.getInt("tin");
                grades = resultSet.getString("grades");
                switch (grades) {
                    case "A": {
                        score = (float) 4.0;
                        break;
                    }
                    case "A+": {
                        score = (float) 4.0;
                        break;
                    }
                    case "B+": {
                        score = (float) 3.5;
                        break;
                    }
                    case "B": {
                        score = (float) 3.0;
                        break;
                    }
                    case "C+": {
                        score = (float) 2.5;
                        break;
                    }
                    case "C": {
                        score = (float) 2.0;
                        break;
                    }
                    case "D": {
                        score = (float) 1.5;
                        break;
                    }
                    case "D+": {
                        score = (float) 1.0;
                        break;
                    }
                    case "F": {
                        score = 0;
                        break;
                    }
                    default: {
                        score = 0;
                    }
                }
                sum = (float) (sum + (float) (score * tin));
                Tin = Tin + tin;
            }
            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Tin == 0) {
            return 0;
        }
        return (float) (sum / Tin);
    }
}
