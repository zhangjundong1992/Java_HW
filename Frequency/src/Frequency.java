import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/*统计《小王子》中单词的频率*/
public class Frequency {
    public static void main(String[] args) {
        var wordsMap = getWordsMap();/*单词与其原型的字典，考虑了单复数，时态*/
        var disableWords = getDisableWords();/*停用字典，考虑了介词等虚词*/

        /*统计单词出现频率*/
        var counterMap = new HashMap<String, Integer>();
        var numCal = 0;/*用于记录统计的单词个数*/
        var numSkip = 0;/*用于记录未统计的单词的个数*/
        var numDisable = 0;/*用于记录未统计的单词的个数*/
        try {
            /*按行读取文件内容*/
            var file = new File(System.getProperty("user.dir") + "\\src\\Little Prince.txt");
            var bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                var words = line.split("\\W+");/*把每个单词切分出来*/
                for (String word : words) {
                    /*将单词转换为小写形式*/
                    word = word.toLowerCase();
                    /*转换为原型*/
                    word = wordsMap.get(word);
                    /*如果单词不在我们的字典中，则跳过对此单词的统计*/
                    if (!wordsMap.containsKey(word)) {
                        numSkip++;
                        continue;
                    }
                    /*如果是停用词，跳过统计*/
                    if (disableWords.contains(word)) {
                        numDisable++;
                        continue;
                    }
                    /*对单词进行统计*/
                    numCal++;
                    counterMap.put(word, counterMap.containsKey(word) ? counterMap.get(word) + 1 : 1);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        /*排序*/
        var list = new ArrayList<>(counterMap.entrySet());
        //降序排序
        list.sort((e1, e2) -> {
            return e2.getValue().compareTo(e1.getValue());
        });

        System.out.println("频率最高的10个单词为：");
        for (int i = 0; i < 10; i++) {
            System.out.println(list.get(i).getKey() + " : " + list.get(i).getValue());
        }

        System.out.println("------------------------------------");
        System.out.println("统计到的单词个数为： " + numCal);
        System.out.println("未统计到的单词个数为： " + numSkip);
        System.out.println("停用词个数为： " + numDisable);
    }

    /*构造单词与原型的HashMap*/
    private static HashMap<String, String> getWordsMap() {
        var map = new HashMap<String, String>();
        try {
            /*按行读取文件内容*/
            var file = new File(System.getProperty("user.dir") + "\\src\\lemmas.txt");
            var bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                var words = line.strip().split("\\s+");/*把每个单词切分出来*/
                for (String word : words) {
                    if (!map.containsKey(word)) {
                        map.put(word, words[0]);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /*构造停用词库*/
    private static ArrayList<String> getDisableWords() {
        var disableWords = new ArrayList<String>();
        try {
            /*按行读取文件内容*/
            var file = new File(System.getProperty("user.dir") + "\\src\\disablewords.txt");
            var bufferedReader = new BufferedReader(new FileReader(file));
            String word = null;
            while ((word = bufferedReader.readLine()) != null) {
                word = word.strip();
                if (!disableWords.contains(word)) {
                    disableWords.add(word);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return disableWords;
    }
}
