package com.liu.spring.springzuul;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringZuulApplicationTests {

//    @Test
//    public void contextLoads() {
//    }

//    private static int count = 0;
//
//    private static AtomicInteger count1 = new AtomicInteger();
//
//    public static void main(String[] args) {
//        for (int i = 0; i < 2; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(10);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    //每个线程让count自增100次
//                    for (int i = 0; i < 100; i++) {
////                        System.out.println(Thread.currentThread().getName() +  " aaa: " + count);
//                        count++;
//                        count1.incrementAndGet();
//                    }
//                }
//            }).start();
//        }
//
//        try{
//            System.out.println(count);
//            System.out.println(count1);
//            Thread.sleep(2000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(count);
//        System.out.println(count1);
//    }




    //冒泡
//    public static void main(String[] args) {
//        int[] arr={6,3,8,2,9,1,12,2344,34,45,54};
//        int jjj = 0;
//
//
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = 0; j < arr.length - 1 -i; j++) {
//
////                if(arr[i] > arr[j]){
////                    jjj++;
////                    int a = arr[i];
////                    arr[i] = arr[j];
////                    arr[j] = a;
////                }
//                if(arr[j] > arr[j + 1]){
//                    jjj++;
//                    int a = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = a;
//                }
//
//            }
//        }
//
//
//
//
//
//
//
//
//
//        //冒泡
////        for (int i = 0; i < arr.length; i++) {
////            for (int j = 0; j < arr.length; j++) {
////                if(arr[i] < arr[j]){
////                    jjj++;
////                    int a = arr[i];
////                    arr[i] = arr[j];
////                    arr[j] = a;
////                }
////            }
////        }
//
//
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }
//        System.out.println("次数：" + jjj);
//
//
//
//    }
//


//    public static void main(String[] args) {
//        int[] arr={6,3,8,2,9,1,12,2344,34,45,54};
//        int jjj =0;
//
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = 0; j < arr.length - 1 -i; j++) {
//                if(arr[j] > arr[j + 1]){
//                    jjj++;
//                    int aaa = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = aaa;
//                }
//
//            }
//        }
//
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }
//        System.out.println("次数：" + jjj);
//
//    }




    //冒泡，实际是每一次都是前一个和后一个比，把每次小的放前面。
//    public static void main(String[] args) {
//        int[] arr = {6,3,8,2,9,1,12,2344,34,45,54};
//
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = 0; j < arr.length - 1 - i; j++) {
//                if(arr[j] > arr[j + 1]){
//                    int aaa = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = aaa;
//                }
//            }
//        }
//
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }
//    }









    //选择排序 数据规模越小越好,
//    public static void main(String[] fff) {
//        int[] arr = {6,3,8,2,9,1,12,2344,34,45,54};
//        int jjj = 0;
//
//        for (int i = 0; i < arr.length; i++) {
//            int minIndex = i;
//            for (int j = i; j < arr.length; j++) {
//                if(arr[j] < arr[minIndex]){
//                    minIndex = j;
//                }
//            }
//            int aaa = arr[i];
//            arr[i] = arr[minIndex];
//            arr[minIndex] = aaa;
//        }
//
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }
//        System.out.println("次数：" + jjj);
//    }

    //选择排序，时间复杂度，所以，总量越小越适合。实际是一个一个跟所有的比，每次找出最小的放前面。
//    public static void main(String[] fff) {
//        int[] arr = {6,3,8,2,9,1,12,2344,34,45,54};
//
//        for (int i = 0; i < arr.length; i++) {
//            int minIndex = i;
//            for (int j = i; j < arr.length; j++) {
//                if(arr[j] < arr[minIndex]){
//                    minIndex = j;
//                }
//            }
//            int aaa = arr[i];
//            arr[i] = arr[minIndex];
//            arr[minIndex] = aaa;
//        }
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }
//    }


    //插入排序
//    public static void main(String[] fff) {
//        int[] arr = {6,3,8,2,9,1,12,2344,34,45,54};
//
//        int current;
//        int index = 0;
//        for (int i = 0; i < arr.length - 1; i++) {
//            current = arr[i + 1];
//            index = i;
//            while (index >= 0 && current < arr[index]){
//                arr[index + 1] = arr[index];
//                index--;
//            }
//            arr[index + 1] = current;
//        }
//
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }
//    }

    static int[] arr = {6,3,8,2,9,1,12,2344,34,45,54};


    //冒泡
//    public static void main(String[] fff) {
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = 0; j < arr.length - 1 - i; j++) {
//                if(arr[j + 1] < arr[j]){
//                    int aaa = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = aaa;
//                }
//            }
//
//        }
//        sout();
//    }

//    //选择,实际上是一个一个和每一个比，将最小的排前面,每次大循环只换一次位置
//    public static void main(String[] fff) {
//        for (int i = 0; i < arr.length; i++) {
//            int minIndex = i;
//            for (int j = i; j < arr.length; j++) {
//                if(arr[j] < arr[minIndex]){
//                    minIndex = j;
//                }
//            }
//            int aaa = arr[i];
//            arr[i] = arr[minIndex];
//            arr[minIndex] = aaa;
//        }
//        sout();
//    }


    //插入排序，从第二个数开始，与前面的有序的数列比较插入，每个大循环只换一次位置
//    public static void main(String[] fff) {
//        for (int i = 0; i < arr.length - 1; i++) {
//            int current = arr[i + 1];
//            int preIndex = i;
//            while (preIndex >= 0 && current > arr[preIndex]){
//                arr[preIndex + 1] = arr[preIndex];
//                preIndex--;
//            }
//            arr[preIndex + 1] = current;
//        }
//        sout();
//    }


    //归并排序
//    public static void main(String[] fff) {
//        arr = msort(arr);
//        sout();
//    }
//
//
//    public static int[] msort(int[] array)
//    {
//        if(array.length < 2)
//        {
//            return array;
//        }
//        int mid = array.length / 2;
//        int[] left = Arrays.copyOfRange(array, 0, mid);
//        int[] right= Arrays.copyOfRange(array, mid, array.length);
//
//        return merge(msort(left),msort(right));
//    }
//
//    public static int[] merge(int[] left, int[] right)
//    {
//        int[] result = new int[left.length + right.length];
//        for (int index = 0,i = 0, j = 0; index < result.length; index++)
//        {
//            if(i >= left.length){
//                result[index] = right[j++];
//            }else if(j >= right.length){
//                result[index] = left[i++];
//            }else if(left[i] > right[j]){
//                result[index] = right[j++];
//            }else{
//                result[index] = left[i++];
//            }
//        }
//        return result;
//    }




    //归并排序
//    public static void main(String[] args) {
//        arr = morge(arr);
//        sout();
//    }
//
//    public static int[] morge(int[] array){
//
//        if(array.length < 2){
//            return array;
//        }
//
//        int center = array.length / 2;
//        int[] left = Arrays.copyOfRange(array, 0 , center);
//        int[] right= Arrays.copyOfRange(array, center, array.length);
//
//        return mMorge(morge(left), morge(right));
//
//    }
//
//    public static int[] mMorge(int[] left, int[] right)
//    {
//        int[] result = new int[left.length + right.length];
//        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
//
//            if(i >= left.length){
//                result[index] = right[j++];
//            }else if(j >= right.length){
//                result[index] = left[i++];
//            }else if(left[i] > right[j]){
//                result[index] = right[j++];
//            }else{
//                result[index] = left[i++];
//            }
//        }
//        return result;
//    }



    //冒泡,是前一个和后一个比，小的放前面
//    public static void main(String[] fff) {
//
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = 0; j < arr.length - 1 -i; j++) {
//                if(arr[j] > arr[j + 1]){
//                    int aaa = arr[j + 1];
//                    arr[j + 1] = arr[j];
//                    arr[j] = aaa;
//                }
//            }
//        }
//        sout();
//
//    }

    //选择 是每次选出最小的，然后与当前大循环的位置置换一次
//    public static void main(String[] fff) {
//        for (int i = 0; i < arr.length; i++) {
//            int minIndex = i;
//            for (int j = i; j < arr.length; j++) {
//                if(arr[j] < arr[minIndex]){
//                    minIndex = j;
//                }
//            }
//            int aaa = arr[minIndex];
//            arr[minIndex] = arr[i];
//            arr[i] = aaa;
//        }
//        sout();
//    }

    //插入 每次当前的和前面已排序好的序列来比
//    public static void main(String[] fff) {
//        for (int i = 0; i < arr.length - 1; i++) {
//            int current = arr[i + 1];
//            int index = i;
//            while (index >= 0 && arr[index] > current){
//                arr[index + 1] = arr[index];
//                index--;
//            }
//            arr[index + 1] = current;
//        }
//        sout();
//    }

//    public static void main(String[] fff) {
//        for (int i = 0; i < arr.length - 1; i++) {
//            int current = arr[i + 1];
//            int index = i;
//            while (index >= 0 && arr[index] > current){
//                arr[index + 1] = arr[index];
//                index--;
//            }
//            arr[index + 1] = current;
//        }
//        sout();
//    }



    //归并
//    public static void main(String[] fff) {
//        arr = morge(arr);
//        sout();
//
//    }
//
//    public static int[] morge(int[] array){
//        if(array.length < 2){
//            return array;
//        }
//        int center = array.length / 2;
//        int[] left = Arrays.copyOfRange(array, 0, center);
//        int[] right = Arrays.copyOfRange(array, center, array.length);
//        return mMorge(morge(left), morge(right));
//    }
//    public static int[] mMorge(int[] left, int[] right){
//        int[] result = new int[left.length + right.length];
//        for (int index = 0,i = 0, j = 0; index < result.length; index++) {
//            if(i >= left.length){
//                result[index] = right[j++];
//            }else if(j >= right.length){
//                result[index] = left[i++];
//            }else if(left[i] > right[j]){
//                result[index] = right[j++];
//            }else{
//                result[index] = left[i++];
//            }
//        }
//        return result;
//    }

    static int[] aaa = new int[]{1,2,3,4,5,7,9};

    //二分查找
    public static void main(String[] args) {

        int value = 7;
        int start = 0;
        int end = aaa.length - 1;

        while (start <= end)
        {
            int center = (start + end) / 2;
            if(value == aaa[center]){
                System.out.println(center);
                break;
            }
            if(value < aaa[center]){
                end = center - 1;
            }
            if(value > aaa[center]){
                start = center + 1;
            }
        }
    }



    public static void sout(){
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
