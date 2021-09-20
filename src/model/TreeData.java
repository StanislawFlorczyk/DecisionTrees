/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author Stasio
 */
public class TreeData implements Serializable
{
    /**
     * Główny obiekt programu komunikujący sie z użytkownikiem.
     */
    public static Model model;
    public Integer level;
    /**
     * Ilość liści w poddrzewie
     */
    public Integer leafs;
    /**
     * Pochodzenie - nazwa pliku.
     */
    public String fileName;
    /**
     * Zmienna sygnałowa.<br><br> 
     * &#09 true - liść,<br>
     * &#09 false - węzeł.      
     */
    public boolean isLeaf; 
    /**
     * Informacja o gałęzi dochodzącej do tego węzła
     */
    public String up;    
    /**
     * Informacja tekstowa zależna od typu elementu:<br><br>
     * &#09 W przypadku liści - etykieta klasy.<br>
     * &#09 W przypadku węzła - nazwa testowanego atrybutu. 
     */
    public String category;
    /**
     *  Lista gałęzi.
     */
    public ArrayList<TreeData> branches = new ArrayList();   
    /**
     * Numer testu wykonywanego w węźle.
     */
    public int test;             
    /**
     * Test na atrybucie:.<br><br>
     * &#09 true - ilościowym,<br>
     * &#09 false - jakościowym.
     */
    public boolean testType;
    /**
     * Wartości dla danego testu jakościowego
     */
    public ArrayList testUniquesValues;
    /**
     * Punkt Zmiany Klasy w przypadku testów ilościowych
     */
    public Double breakPoint;
    /**
     * Zbiór danych dochodzących do węzła
     */
    private List<ArrayList> table = new ArrayList();  
    /**
     * Zbiór dostępnych testów dochodzących do węzła
     */
    private List<Integer> availableTest = new ArrayList();
    /**
     * Dla uproszczenia zapisów pole odpowiadające liście unikalnych wartości klas
     * model.trainingSet.uniqueValues.get(model.trainingSet.uniqueValues.size()-1)
     */
    private static ArrayList<String> uniqueClassValues = new ArrayList(); 

    /**
     * Konstruktor obiektu TreeData dla pierwszego elementu.
     * @param t główny obiekt programu.
     */
    public TreeData(Model m)
    {
        model = m;  
        preBuild(); 
        isLeaf = checkStop();
        if(!isLeaf)
        {
            test = chooseTest();
            List<Integer> newAvailableTest = new ArrayList();
            for(int i = 0; i < availableTest.size(); i++)
            {
                if(availableTest.get(i) != test)
                {
                    newAvailableTest.add(availableTest.get(i));
                }
            }
            category = model.trainingSet.headlines.get(test);
            testType = model.trainingSet.types.get(test);
            if(testType)
            {
                //Ilosciowa
                breakPoint = getBreakPoint(test);
                branches.add(new TreeData(getNewTableInequality(test,breakPoint,true),newAvailableTest,"<= " + breakPoint.toString()));
                branches.add(new TreeData(getNewTableInequality(test,breakPoint,false),newAvailableTest,"> " + breakPoint.toString()));      
            }else
            {
                testUniquesValues = model.trainingSet.uniqueValues.get(test);
                //Jakościowa
                for(int i = 0; i<testUniquesValues.size(); i++)
                {     
                    branches.add(new TreeData(getNewTableIdentity((String)testUniquesValues.get(i)),newAvailableTest,(String)testUniquesValues.get(i)));      
                }
            }
        }
        postBuild();
    }   
    /**
     * Konstruktor używany w rekurancji do tworzenia modelu drzewa
     * @param t podzbiór dochodzący do węzła.
     * @param at dostępne atrybuty dochodzące do węzła.
     * @param p gałąź dochodząca do węzła.
     */
    public TreeData(List<ArrayList> t, List<Integer> at, String p)
    {
        table = t;
        fileName = model.trainingSet.fileName;
        availableTest = at;
        up = p;
        isLeaf = checkStop();
        if(!isLeaf)
        {
            test = chooseTest();
            createSubTrees();    
        }
    }
    /**
     * Rekurencyjne tworzenie poddrzew.
     */
    private void createSubTrees() 
    {
        List<Integer> newAvailableTest = new ArrayList();
        for(int i = 0; i < availableTest.size(); i++)
        {
            if(availableTest.get(i) != test)
            {
                newAvailableTest.add(availableTest.get(i));
            }
        }
        category = model.trainingSet.headlines.get(test);
        testType = model.trainingSet.types.get(test);
        if(testType)
        {
            //Ilosciowa
            breakPoint = getBreakPoint(test);
            branches.add(new TreeData(getNewTableInequality(test,breakPoint,true),
                                      newAvailableTest,
                                      "<= " + breakPoint.toString()));
            branches.add(new TreeData(getNewTableInequality(test,breakPoint,false),
                                      newAvailableTest,
                                      "> " + breakPoint.toString()));      
        }else
        {
            //Jakościowa 
            testUniquesValues = model.trainingSet.uniqueValues.get(test);
            for(int i = 0; i < testUniquesValues.size(); i++)
            {     
                branches.add(new TreeData(getNewTableIdentity((String)model.trainingSet.uniqueValues.get(test).get(i)),
                                          newAvailableTest,
                                          (String)model.trainingSet.uniqueValues.get(test).get(i)));      
            }
        }
    }
    /**
     * Funkcja Uruchamiana przed procesem konstrukcji.
     */
    private void preBuild()
    {
        up = "Drzewo Decyzyjne:";
        table = model.trainingSet.table;
        fileName = model.trainingSet.fileName;
        uniqueClassValues = model.trainingSet.uniqueValues.get(model.trainingSet.uniqueValues.size()-1);
        for(int i = 0; i < model.trainingSet.headlines.size()-1; i++)
        {
            if(!model.parameters.skippedTests.contains(model.trainingSet.headlines.get(i)))
            {
                availableTest.add(i);
            } 
        }
    }
    /**
     * Funkcja uruchamiana po procesie konstrukcji.
     */
    private void postBuild()
    {
        countLeaves();
        setLevel(1);
    }
    /**
     * Funkcja sprawdzająca kryterium stopu.
     * @return 
     * &#09 true - liśc(stop),<br>
     * &#09 false - węzeł.
     */
    private boolean checkStop() 
    {
        if(table.get(0).isEmpty())
        {
            category = "Pusto";
            branches.clear();
            return true;
        }
        if(availableTest.isEmpty())
        {
            category = getMostFrequentClass();
            branches.clear();
            return true;
        }
        String tmp = getMostFrequentClass();
        Integer count = 0;
        for(Object o : table.get(table.size()-1))
        {
            if(o.equals(tmp))
            {
                count++;
            }
        }
        if(model.parameters.accuracy == 100)       
        {
            if(count == table.get(table.size()-1).size())
            {
                category = tmp;
                return true;
            }
        }else
        {
            Double accu;
            accu = count/ Double.valueOf(table.get(table.size()-1).size()) * 100;
            if(accu >= model.parameters.accuracy)
            {
                category = tmp;
                return true;
            }
        }
        category = null;
        return false;
    }
    /**
     * Ustawienie Klasy w liściu
     */
    private String getMostFrequentClass()
    {
        List<Integer> count = new ArrayList();
        for(int i = 0; i < uniqueClassValues.size(); i++)
        {
            count.add(0);
        }
        for(int i = 0; i < uniqueClassValues.size(); i++)
        {
            for(Object s : table.get(table.size()-1))
            {
                if(s.equals(uniqueClassValues.get(i)))
                {
                    count.set(i, count.get(i)+1);
                }
            }
        }  
        int max = count.get(0);  
        String ret = uniqueClassValues.get(0);
        for (int i = 0; i < count.size(); i++)
        {
            if (count.get(i) > max)
            {
                max = count.get(i);
                ret = uniqueClassValues.get(i);
            }
        }
        return ret;
    }
    /**
     * Wybór testu do węzła za pomocą algorytmu C4.5.
     * @return numer testu.
     */
    private int chooseTest() 
    {
        double en = entropy(countClass());  
        List<Double> infoGain = new ArrayList();
        for(int i = 0; i <= Collections.max(availableTest); i++)
        {
            infoGain.add(Double.valueOf(-1));
        }
        for(int i = 0; i < availableTest.size(); i++)
        {
            int z = availableTest.get(i);
            if(model.trainingSet.types.get(z))
            {
                //Ilościowy
                infoGain.set(i,inequalityTest(z));
            }else
            {
                //Jakościowy  
                infoGain.set(i,identityTest(z));
            } 
        }   
        
        for(int i = 0; i < infoGain.size(); i++)
        {
            if(infoGain.get(i) == -1.0)
            {
                infoGain.remove(i);
                i--;
            }
        }
        for(int i = 0; i < infoGain.size(); i++)
        {
            infoGain.set(i, (en - infoGain.get(i))/en);
            
        }
        for(int i = 0; i < infoGain.size(); i++)
        {
            if(infoGain.get(i) == Collections.max(infoGain))
            {
                return availableTest.get(i);
            }
        }
        System.exit(101);
        return -1;
    }
    /**
     * Funkcja obliczająca entropie - nieuporzadkowanie zbioru.
     * @param cs lista zawierająca zbiór.
     * @return wartość entropii dla zbioru.
     */
    private double entropy(List<Integer> cs) 
    { 
        double x = 0,f;
        int size = 0;
        for(Integer i : cs)
        {
            size += i;
        }  
        for(int i = 0; i < cs.size(); i++)
        {
            if(cs.get(i) != 0)
            {
                f = (Double.valueOf(cs.get(i))/Double.valueOf(size));
                x += f * (Math.log10(f)/ Math.log10(2));
            }   
        }
        return -x;
    }
    /**
     * Funkcja obliczająca entropie ze względu na wskazany atrybut.
     * @param attrIndex numer atrybutu.
     * @param countAttr ilość wystapień wartośi danego atrybutu dlakloejnych klas.
     * @return entropia ze wzgledu na analizowany atrybut.
     */
    private double entropyIdentity(int attrIndex, List<ArrayList> countAttr) 
    { 
        double x = 0;
        int count;
        List<Integer> tmp = new ArrayList();
        for(int i = 0; i < model.trainingSet.uniqueValues.get(attrIndex).size(); i++)
        {
            count = 0;
            for(Object o : table.get(attrIndex))
            {
                if(o.equals(model.trainingSet.uniqueValues.get(attrIndex).get(i)))
                {
                    count++;
                }
            }
            tmp.clear();
            for(ArrayList<Integer> a : countAttr)
            {
                tmp.add(a.get(i));  
            }
            x += (Double.valueOf(count)/Double.valueOf(table.get(attrIndex).size())) * entropy(tmp);
        }  
        return x;
    }
    /**
     * Funkcja przeprowadzajaca test nieruwnościowy.
     * @param attrIndex numer atrybutu.
     * @return entropia danego atrybutu.
     */
    private double inequalityTest(int attrIndex) 
    {
        List<IneqSort> is = new ArrayList();
        for(int i = 0; i < table.get(0).size(); i++)
        {
            is.add(new IneqSort(Double.valueOf((String)table.get(attrIndex).get(i)),(String)table.get(table.size()-1).get(i)));
        }
        Collections.sort(is);
        List<Double> changePoints = new ArrayList();
        String cat = is.get(0).category;
        double buff = is.get(0).data;
        for(int i = 1; i < is.size(); i++)
        {
            if(!cat.equals(is.get(i).category))
            {
                changePoints.add((buff + is.get(i).data)/2); 
                cat = is.get(i).category;
            }
            buff = is.get(i).data;
        }
        double minEntropy = 1000.0;
        for(Double d : changePoints)
        {
            double countLeft = 0;
            double countRight = 0;
            List<Integer> countPerClassLeft = new ArrayList();
            List<Integer> countPerClassRight = new ArrayList();
            for(int i = 0; i < uniqueClassValues.size(); i++)
            {
                countPerClassLeft.add(0);
                countPerClassRight.add(0);
            }
            for(IneqSort o : is)
            {
                if(o.data <= d)
                {
                    countLeft++;
                    for(int i = 0; i < uniqueClassValues.size(); i++)
                    {
                        if(uniqueClassValues.get(i).equals(o.category))
                        {
                            countPerClassLeft.set(i, countPerClassLeft.get(i)+1);
                        }
                    }
                }else
                {
                    countRight++;
                    for(int i = 0; i < uniqueClassValues.size(); i++)
                    {
                        if(uniqueClassValues.get(i).equals(o.category))
                        {
                            countPerClassRight.set(i, countPerClassRight.get(i)+1);
                        }
                    }
                }  
            }
            double x; 
            x =  countLeft/(countLeft+countRight) * entropy(countPerClassLeft);
            x += countRight/(countLeft+countRight) * entropy(countPerClassRight);
            if(x < minEntropy)
            {
                minEntropy = x;
            }
        }
        return minEntropy;
    }
    /**
     * Funkcja przeprowadzajaca test tożsamościowy.
     * @param attrIndex numer atrybutu.
     * @return entropia danego atrybutu.
     */
    private double identityTest(int attrIndex) 
    {
        List<ArrayList> countAttr = new ArrayList();
        List<Integer> countAttrPerClass = new ArrayList();
        String catClass;
        for(int z = 0; z < uniqueClassValues.size(); z++)
        {
            countAttr.add(z, new ArrayList<Integer>());
        }       
        for(int s = 0; s < uniqueClassValues.size(); s++)
        {
            catClass = uniqueClassValues.get(s);
            countAttrPerClass.clear();
            for(Object zz : model.trainingSet.uniqueValues.get(attrIndex))
            {
                countAttrPerClass.add(0);
            }
            for(int j = 0; j < table.get(attrIndex).size(); j++)
            {     
                for(int i = 0; i < model.trainingSet.uniqueValues.get(attrIndex).size(); i++)
                {
                    if(model.trainingSet.uniqueValues.get(attrIndex).get(i).equals(table.get(attrIndex).get(j)) &&
                       table.get(table.size()-1).get(j).equals(catClass)) 
                    {
                        countAttrPerClass.set(i, countAttrPerClass.get(i)+1);
                    }     
                }
            }
            ///
            for(int i = 0; i < countAttrPerClass.size(); i++)
            {
                countAttr.get(s).add(i, countAttrPerClass.get(i));
            }
        }
        return entropyIdentity(attrIndex,countAttr);
    }/**
     * Funkcja zliczająca ilość elementów należacych do poszczególnych klas.
     * @return lista sum elementów poszczególnych klas.
     */
    private List<Integer> countClass()
    {
        List<Integer> count = new ArrayList();
        for(int i = 0; i < uniqueClassValues.size(); i++)
        {
            count.add(0);
        }
        for(int i = 0; i < uniqueClassValues.size(); i++)
        {
            for(Object s : table.get(table.size()-1))
            {
                if(s.equals(uniqueClassValues.get(i)))
                {
                    count.set(i, count.get(i)+1);
                }
            }
        }  
        return count;
    }
    /**
     * * Funkcja zwraca podzbiór danych przekazywany do następnego węzła w przypadku testu nierównościowego.
     * @param val warość atrybuty jakościowego
     * @return podzbiór elementów.
     */
    private List<ArrayList> getNewTableIdentity(String val)
    {
        List<ArrayList> tab = new ArrayList();
        for(ArrayList o : table)
        {
            tab.add(new ArrayList());
        }
        for(int j = 0; j < table.get(0).size(); j++)
        {
            String s1,s2;
            s1 = (String)table.get(test).get(j);
            s2 = val;
            if(s1.equals(s2))
            {
                for(int i =0; i < table.size(); i++)
                {
                    tab.get(i).add(table.get(i).get(j));
                }
            }
        }
        return tab;   
    }
    /**
     * Funkcja zwraca punkt który dzieli zbiór atrybutów ilościowych w sposób minimalizujący entropie.
     * @param attrIndex numer atrybutu.
     * @return punkt podziału.
     */
    private double getBreakPoint(int attrIndex) 
    {
        double ret = 0.0;
        List<IneqSort> is = new ArrayList();
        for(int i = 0; i < table.get(0).size(); i++)
        {
            is.add(new IneqSort(Double.valueOf((String)table.get(attrIndex).get(i)),(String)table.get(table.size()-1).get(i)));
        }
        Collections.sort(is);
        List<Double> changePoints = new ArrayList();
        String cat = is.get(0).category;
        double buff = is.get(0).data;
        for(int i = 1; i < is.size(); i++)
        {
            if(!cat.equals(is.get(i).category))
            {
                changePoints.add((buff + is.get(i).data)/2); 
                cat = is.get(i).category;
            }
            buff = is.get(i).data;
        }
        double minEntropy = 1000.0;
        for(Double d : changePoints)
        {
            double countLeft = 0;
            double countRight = 0;
            List<Integer> countPerClassLeft = new ArrayList();
            List<Integer> countPerClassRight = new ArrayList();
            for(int i = 0; i < uniqueClassValues.size(); i++)
            {
                countPerClassLeft.add(0);
                countPerClassRight.add(0);
            }
            for(IneqSort o : is)
            {
                if(o.data <= d)
                {
                    countLeft++;
                    for(int i = 0; i < uniqueClassValues.size(); i++)
                    {
                        if(uniqueClassValues.get(i).equals(o.category))
                        {
                            countPerClassLeft.set(i, countPerClassLeft.get(i)+1);
                        }
                    }
                }else
                {
                    countRight++;
                    for(int i = 0; i < uniqueClassValues.size(); i++)
                    {
                        if(uniqueClassValues.get(i).equals(o.category))
                        {
                            countPerClassRight.set(i, countPerClassRight.get(i)+1);
                        }
                    }
                }  
            }
            double x; 
            x =  countLeft/(countLeft+countRight) * entropy(countPerClassLeft);
            x += countRight/(countLeft+countRight) * entropy(countPerClassRight);
            if(x < minEntropy)
            {
                minEntropy = x;
                ret = d;
            }
        }
        return ret;
    }
    /**
     * Funkcja zwraca podzbiór danych przekazywany do następnego węzła w przypadku testu nierównościowego.
     * @param t numer testu.
     * @param bp punkt podziału.
     * @param side strona<br> 
     *  &#09 true - mniejsze równe.
     *  &#09 false - większe.
     * @return podzbiór elementów.
     */
    private List<ArrayList> getNewTableInequality(int t, double bp, boolean side) 
    {
        List<ArrayList> tab = new ArrayList();
        for(ArrayList o : table)
        {
            tab.add(new ArrayList());
        }
        for(int j = 0; j < table.get(0).size(); j++)
        {
            double s1,s2;
            s1 = Double.valueOf((String)table.get(test).get(j));
            s2 = bp;
            if(side)
            {
                if(s1 <= s2)
                {
                    for(int i =0; i < table.size(); i++)
                    {
                        tab.get(i).add(table.get(i).get(j));
                    }
                }
            }else
            {
                if(s1 > s2)
                {
                    for(int i =0; i < table.size(); i++)
                    {
                        tab.get(i).add(table.get(i).get(j));
                    }
                }
            }
        }
        return tab;  
    }
    /**
     * Funkcja licząca ilość liści w gałęziach
     */
    private Integer countLeaves()
    {
        leafs = 0;
        if(isLeaf)
        {
            leafs++;
            return leafs;
        }else
        {
            ArrayList<Integer> list = new ArrayList<>();
            for(TreeData o : branches)
            {      
                list.add(o.countLeaves());
            }
            for(Integer i : list)
            {
                leafs += i; 
            }
            return leafs;
        }           
    }
    /**
     * Funkcja ustawiająca poziomy na którym znajdużą się węzły.
     */
    private void setLevel(Integer x)
    {
        level = x;
        if(!isLeaf)
        {
            for(TreeData o : branches)
            {      
                o.setLevel(level+1);
            }
        }
    }
    /**
     * Funkcja zwracająca ilość węzłów w najdłuższej gałęzi wychądzocej z węzła.
     * @return Najdłuższa gałąź od danego węzła.
     */
    public Integer getHeight()
    {
        if(isLeaf)
        {
            return 1;
        }else
        {
            Integer maxLevel = 0;
            for(TreeData o : branches)
            {      
                Integer x = o.getHeightReku();
                if(x > maxLevel)
                {
                    maxLevel = x;
                }
            }
            return maxLevel - level + 1;
        }
    }
    public Integer getHeightReku()
    {
        if(isLeaf)
        {
            return level;
        }else
        {
            Integer maxLevel = 0;
            for(TreeData o : branches)
            {      
                Integer x = o.getHeightReku();
                if(x > maxLevel)
                {
                    maxLevel = x;
                }
            }
            return maxLevel;
        }
    }
    /**
     * Służaca do stworzenia regułowej reprezentacji drzewa decyzyjnego.
     * @return Łańcuch zawierający reuły decyzyjne.
     */
    public String getRules()
    {
        if(isLeaf)
        {
            return "Wszystkie dane należą do klasy " + category;
        }else
        {
            String rules;
            rules = "Jeżeli " + category + " jest ";
            rules = getRules(rules, true);
            rules = rules.replace("[", "");
            rules = rules.replace("]", "");
            rules = rules.replace("\n, ", "\n");
            rules = "\nReprezentacja Regułowa drzewa decyzyjnego: \n\n" + rules;
            return rules;
        }
    }
    /**
     * Funkcja używana przy tworzeniu reguł decyzyjnych pomagająca w rekurencyjnym przeszukiwaniu drzewa.
     * @param str zmienna wyjściowa do której dodawany zostanie tekst.
     * @param start zmienna sygnałowa pierwsego uruchomienia rekurencji.
     * @return lista reguł.
     */
    private String getRules(String str, Boolean start)
    {
        String ret = str;
        if(!start)
        {
            ret = str.concat(up);
        }
        String l = "";
        if(isLeaf)
        {
            l = ret.concat(" to - " + category + "\n");
        }else
        {
            if(!start)
            {
                ret = ret.concat(", " + category + " jest ");
            }
            for(TreeData td : branches)
            {
                l = l.concat(td.getRules(ret,false));
            }
        } 
        return l;
    }  
}
