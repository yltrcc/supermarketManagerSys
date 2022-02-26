import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Package: PACKAGE_NAME
 * Date：2022-02-26
 * Time：16:50
 * Description：TODO
 *
 * @author yltrcc
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("商超购物管理系统 商品维护");
        boolean flag = true;
        while (flag) {
            Scanner input = new Scanner(System.in);
            System.out.println("**********************************");
            System.out.println("        1、查询商品  ");
            System.out.println("        2、增加商品  ");
            System.out.println("        3、更新商品  ");
            System.out.println("        0、退出系统  ");
            System.out.println("**********************************");
            System.out.println("    请选择，输入上面其中的数字");
            try {
                int contents = input.nextInt();
                if (contents == 1) {
                    query();
                } else if (contents == 2) {
                    insert();
                } else if (contents == 3) {
                    update();
                }  else if (contents == 0) {
                    System.out.println("已退出当前系统，数据已保存");
                    flag = false;
                }
            } catch (Exception e) {
                System.out.println("输入错误！请重新输入");
            }
        }
    }



    /**
     * 新增学生
     */
    public static void insert() {
        File file = new File("D:\\test\\superMarket.txt");
        createFile(file);
        try {
            FileWriter fw = new FileWriter(file, true);
            Good good = new Good();
            try {
                System.out.println("执行添加商品操作：");
                Scanner input = new Scanner(System.in);
                System.out.print("添加商品名称：");
                good.setName(input.nextLine());
                System.out.print("添加商品价格：");
                good.setPrice(input.next());
                System.out.print("添加商品数量：");
                good.setCount(input.next());
                if (Integer.valueOf(good.getCount()) < 10 ) {
                    good.setComment("*商品不足10件");
                }
                fw.write(good.toString() + "\n");
                fw.close();
            } catch (Exception e) {
                System.out.println("输入类型错误，请重新添加");
                insert();
            }
            System.out.println("添加完成。请选择功能：1、再次添加  2、退出");
            Scanner input = new Scanner(System.in);
            if (Integer.valueOf(input.next()) == 1) {
                insert();
            } else {
                System.out.println("已退出到上一级目录\n");
            }
        } catch (Exception e) {
            System.out.println("新增数据失败，请重新输入");
            insert();
        }
    }

    /**
     * 查询学生
     */
    public static void query() {
        System.out.println("商品名称    商品价格    商品数量    备注");
        try {
            //1、获取txt文件数据，组装list
            List<Good> list = queryList();
            if (list.size() == 0) {
                System.out.println("数据不存在，请插入相关数据");
            } else {
                for (Good good : list) {
                    System.out.println(good.getName() + "    " + good.getPrice() + "    " + good.getCount() + "    " + good.getComment());
                }
            }
        } catch (Exception e) {
            System.out.println("没有该功能，请重新选择！！\n");
        }
    }

    /**
     * 修改学生
     */
    public static void update() {
        File file = new File("D:\\test\\superMarket.txt");
        createFile(file);
        List<Good> lists = queryList();
        if (lists.size() == 0) {
            System.out.println("该系统中不存在学生，请插入");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.print("请输入商品名称：");
        String name = input.next();
        int num = 0;
        List<Good> listNew = new ArrayList<Good>();
        for (Good good : lists) {
            if (good.getName().equals(name)) {
                num++;
                boolean flag = true;
                while (flag) {
                    System.out.println("请选择修改的选项：1、商品名称 2、商品价格 3、商品数量 6、退出");
                    int contents = input.nextInt();
                    if (contents == 1) {
                        System.out.print("请输入商品名称：");
                        good.setName(input.next());
                    } else if (contents == 2) {
                        System.out.print("请输入商品价格：");
                        good.setPrice(input.next());
                    } else if (contents == 3) {
                        System.out.print("请输入商品数量：");
                        good.setCount(input.next());
                    } else if (contents == 6) {
                        System.out.println("已返回上一级目录\n");
                        flag = false;
                    }
                }
            }
            listNew.add(good);
        }
        try {
            FileWriter fwDelete = new FileWriter(file, false);
            fwDelete.write("");
            fwDelete.close();
            FileWriter fw = new FileWriter(file, true);
            for (Good good : listNew) {
                fw.write(good.toString() + "\n");
            }
            fw.close();
            if (num == 0) {
                System.out.println("修改商品失败，该商品不存在");
            } else {
                System.out.println("修改成功。\n");
            }
            System.out.println("请选择功能：1、再次修改  2、退出");
            if (Integer.valueOf(input.next()) == 1) {
                update();
            } else {
                System.out.println("已退出到上一级目录\n");
            }
        } catch (Exception e) {
            System.out.println("修改学生学习失败！请重试");
            update();
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
                System.out.println("创建本地文件失败，请手动创建。创建目录为：D:\\test\\superMarket.txt");
            }
        }
    }

    public static List<Good> queryList() {
        List<Good> list = new ArrayList<Good>();
        File file = new File("D:\\test\\superMarket.txt");
        createFile(file);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            //使用readLine方法，一次读一行
            while ((s = br.readLine()) != null) {
                String[] strings = s.split(",");
                Good good = new Good();
                good.setName(strings[0]);
                good.setPrice(strings[1]);
                good.setCount(strings[2]);
                good.setComment(strings[3]);
                list.add(good);
            }
            br.close();
        } catch (Exception e) {
        }
        return list;
    }

    static class Good {
        //商品名称
        String name;
        //价格
        String price;
        //数量
        String count;
        //语文
        String comment;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @Override
        public String toString() {
            return name + ',' + price + ',' + count + ',' + comment;
        }
    }
}
