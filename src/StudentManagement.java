import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author: lirui
 * @Date: 2020/12/20 15:50
 */
public class StudentManagement {

    public static void main(String[] args) {
        System.out.println("学生管理系统");
        boolean flag = true;
        while (flag) {
            Scanner input = new Scanner(System.in);
            System.out.println("请选择使用的功能：1、查询  2、增加  3、更新  4、删除  5、退出");
            try {
                int contents = input.nextInt();
                if (contents == 1) {
                    queryStudent();
                } else if (contents == 2) {
                    insertStudent();
                } else if (contents == 3) {
                    updateStudent();
                } else if (contents == 4) {
                    deleteStudent();
                } else if (contents == 5) {
                    System.out.println("已退出当前系统，数据已保存");
                    flag = false;
                }
            } catch (Exception e) {
                System.out.println("输入错误！请重新输入");
            }
        }
    }

    /**
     * 删除学生
     */
    public static void deleteStudent() {
        List<Student> list = queryList();
        if (list.size() == 0) {
            System.out.println("不存在学生，请添加");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.print("请输入学号：");
        int contents = Integer.valueOf(input.next());
        File file = new File("D:\\student\\student.txt");
        List<Student> listNew = new ArrayList<Student>();
        int number = 0;
        for (Student student : list) {
            if (contents == student.getNumber()) {
                number++;
            } else {
                listNew.add(student);
            }
        }
        try {
            FileWriter fwDelete = new FileWriter(file, false);
            fwDelete.write("");
            fwDelete.close();
            FileWriter fw = new FileWriter(file, true);
            for (Student student : listNew) {
                fw.write(student.getStudent() + "\n");
            }
            fw.close();
            if (number == 0) {
                System.out.println("删除学生失败！系统中不存在改学生" + "\n");
            } else {
                System.out.println("改学生已删除，学号为：" + contents + "\n");
            }
            System.out.println("请选择功能：1、继续删除 2、退出");
            if (Integer.valueOf(input.next()) == 1) {
                deleteStudent();
            } else {
                System.out.println("已退出到上一级目录\n");
            }
        } catch (Exception e) {
            System.out.println("删除学生失败！请重试");
            deleteStudent();
        }
    }

    /**
     * 新增学生
     */
    public static void insertStudent() {
        File file = new File("D:\\student\\student.txt");
        createFile(file);
        try {
            FileWriter fw = new FileWriter(file, true);
            Student student = new Student();
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("请输入学号：");
                student.setNumber(input.nextInt());
                System.out.print("请输入姓名：");
                student.setName(input.next());
                System.out.print("请输入年龄：");
                student.setAge(input.nextInt());
                System.out.print("请输入语文成绩：");
                student.setMathematics(input.nextInt());
                System.out.print("请输入数学成绩：");
                student.setChinese(input.nextInt());
                System.out.print("请输入英语成绩：");
                student.setEnglish(input.nextInt());
                fw.write(student.getStudent() + "\n");
                fw.close();
            } catch (Exception e) {
                System.out.println("输入类型错误，请重新添加");
                insertStudent();
            }
            System.out.println("添加完成。请选择功能：1、再次添加  2、退出");
            Scanner input = new Scanner(System.in);
            if (Integer.valueOf(input.next()) == 1) {
                insertStudent();
            } else {
                System.out.println("已退出到上一级目录\n");
            }
        } catch (Exception e) {
            System.out.println("新增学生失败，请重新输入");
            insertStudent();
        }
    }

    /**
     * 查询学生
     */
    public static void queryStudent() {
        System.out.println("请选择查询功能：1、全量查询  2、学号查询  3、退出");
        Scanner input = new Scanner(System.in);
        try {
            int contents = input.nextInt();
            if (contents == 1) {
                //1、获取txt文件数据，组装list
                List<Student> list = queryList();
                if (list.size() == 0) {
                    System.out.println("不存学生，请插入相关学生");
                } else {
                    for (Student student : list) {
                        System.out.println(student.toString());
                    }
                }
                queryStudent();
            } else if (contents == 2) {
                //1、获取txt文件数据，组装list
                System.out.print("请输入学生学号：");
                try {
                    List<Student> list = queryList();
                    contents = input.nextInt();
                    int number = 0;
                    for (Student student : list) {
                        if (contents == student.getNumber()) {
                            System.out.println(student.toString());
                            number++;
                        }
                    }
                    if (number == 0) {
                        System.out.println("查找该学生不存在");
                    }
                } catch (Exception e) {
                    System.out.println("该学生不存在！！！");
                }
                queryStudent();
            } else if (contents == 3) {
                System.out.println("已返回上一级目录\n");
            }
        } catch (Exception e) {
            System.out.println("没有该功能，请重新选择！！\n");
            queryStudent();
        }
    }

    /**
     * 修改学生
     */
    public static void updateStudent() {
        File file = new File("D:\\student\\student.txt");
        createFile(file);
        List<Student> lists = queryList();
        if (lists.size() == 0) {
            System.out.println("该系统中不存在学生，请插入");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.print("请输入学生学号：");
        int number = Integer.valueOf(input.next());
        int num = 0;
        List<Student> listNew = new ArrayList<Student>();
        for (Student student : lists) {
            if (student.getNumber() == number) {
                num++;
                boolean flag = true;
                while (flag) {
                    System.out.println("请选择修改的选项：1、姓名 2、年龄 3、数学 4、语文 5、英语 6、退出");
                    int contents = input.nextInt();
                    if (contents == 1) {
                        System.out.print("请输入姓名：");
                        student.setName(input.next());
                    } else if (contents == 2) {
                        System.out.print("请输入姓名：");
                        student.setAge(Integer.valueOf(input.next()));
                    } else if (contents == 3) {
                        System.out.print("请输入数学：");
                        student.setMathematics(Integer.valueOf(input.next()));
                    } else if (contents == 4) {
                        System.out.print("请输入语文：");
                        student.setChinese(Integer.valueOf(input.next()));
                    } else if (contents == 5) {
                        System.out.print("请输入英语：");
                        student.setEnglish(Integer.valueOf(input.next()));
                    } else if (contents == 6) {
                        System.out.println("已返回上一级目录\n");
                        flag = false;
                    }
                }
            }
            listNew.add(student);
        }
        try {
            FileWriter fwDelete = new FileWriter(file, false);
            fwDelete.write("");
            fwDelete.close();
            FileWriter fw = new FileWriter(file, true);
            for (Student student : listNew) {
                fw.write(student.getStudent() + "\n");
            }
            fw.close();
            if (num == 0) {
                System.out.println("修改学生学习失败，该学生不存在");
            } else {
                System.out.println("修改成功。\n");
            }
            System.out.println("请选择功能：1、再次修改  2、退出");
            if (Integer.valueOf(input.next()) == 1) {
                updateStudent();
            } else {
                System.out.println("已退出到上一级目录\n");
            }
        } catch (Exception e) {
            System.out.println("修改学生学习失败！请重试");
            updateStudent();
        }
    }

    /**
     * 创建文件夹及路径
     *
     * @param file
     */
    public static void createFile(File file) {
        if (!file.exists()) {
            if (!file.isDirectory()) {
                File file2 = new File(file.getParent());
                file2.mkdirs();
            }
            try {
                if (!file.isDirectory()) {
                    file.createNewFile();//创建文件
                }
            } catch (Exception e) {
                System.out.println("创建本地文件失败，请手动创建。创建目录为：D:\\student\\student.txt");
            }
        }
    }

    public static List<Student> queryList() {
        List<Student> list = new ArrayList<Student>();
        File file = new File("D:\\student\\student.txt");
        createFile(file);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            //使用readLine方法，一次读一行
            while ((s = br.readLine()) != null) {
                String[] strings = s.split(",");
                Student student = new Student();
                student.setNumber(Integer.valueOf(strings[0]));
                student.setName(strings[1]);
                student.setAge(Integer.valueOf(strings[2]));
                student.setMathematics(Integer.valueOf(strings[3]));
                student.setChinese(Integer.valueOf(strings[4]));
                student.setEnglish(Integer.valueOf(strings[5]));
                list.add(student);
            }
            br.close();
        } catch (Exception e) {
        }
        return list;
    }

    static class Student {
        //学号
        int number;
        //姓名
        String name;
        //年龄
        int age;
        //数学
        int mathematics;
        //语文
        int chinese;
        //英语
        int english;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getMathematics() {
            return mathematics;
        }

        public void setMathematics(int mathematics) {
            this.mathematics = mathematics;
        }

        public int getChinese() {
            return chinese;
        }

        public void setChinese(int chinese) {
            this.chinese = chinese;
        }

        public int getEnglish() {
            return english;
        }

        public void setEnglish(int english) {
            this.english = english;
        }

        @Override
        public String toString() {
            return "学号：" + number + "  姓名：" + name + " 年龄：" + age +
                    " 数学：" + mathematics + " 语文：" + chinese + " 英语：" + english;
        }

        public String getStudent() {
            return "" + number + "," + name + "," + age + "," + mathematics + "," + chinese + "," + english;
        }
    }
}
