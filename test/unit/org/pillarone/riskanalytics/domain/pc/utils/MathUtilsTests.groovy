package org.pillarone.riskanalytics.domain.pc.utils

import org.pillarone.riskanalytics.core.util.MathUtils
import umontreal.iro.lecuyer.rng.F2NL607

class MathUtilsTests extends GroovyTestCase {

    void testStDev() {
        assertEquals 0, MathUtils.calculateStandardDeviation((double[]) [3])
        assertEquals 11.95, MathUtils.calculateStandardDeviation((double[]) [3, 11.5, 31.5]), 0.01
    }

    void testPercentile() {
        assertEquals 0, MathUtils.calculatePercentile((double[]) [0, 10, 20], 0)
        assertEquals 10, MathUtils.calculatePercentile((double[]) [0, 10, 20], 50)
        assertEquals 20, MathUtils.calculatePercentile((double[]) [0, 10, 20], 100)
        assertEquals 18.3333333333333, MathUtils.calculatePercentile((double[]) [0, 10, 20], 75), 1E-8
        assertEquals 20, MathUtils.calculatePercentile((double[]) [0, 10, 20], 90)
    }


    void testVar() {
        assertEquals 0, MathUtils.calculateVar((double[]) [0, 10, 20], 50)
        assertEquals 10, MathUtils.calculateVar((double[]) [0, 10, 20], 90)
    }

    void testTvar() {
        assertEquals 5, MathUtils.calculateTvar((double[]) [0, 10, 20], 50)
        assertEquals 5, MathUtils.calculateTvar((double[]) [0, 10, 20], 25)
        assertEquals 10, MathUtils.calculateTvar((double[]) [0, 10, 20], 90)
        assertEquals 10, MathUtils.calculateTvar((double[]) [0, 10, 20], 100)
    }

    static List linearSeed = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
    static List linearSeed2 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 1, 2, 3]

    void testRandomSeed() {
        F2NL607 genererator1 = new F2NL607();
        F2NL607 genererator3 = new F2NL607();
        F2NL607 genererator2 = new F2NL607();
        F2NL607 genererator4 = new F2NL607();
        genererator1.setLinearSeed(linearSeed as int[])
        genererator3.setLinearSeed(linearSeed2 as int[])
        genererator2.setLinearSeed(linearSeed as int[])
        genererator4.setLinearSeed(linearSeed2 as int[])

        genererator1.setNonLinearSeed([0, 0, 0] as int[])
        genererator2.setNonLinearSeed([0, 0, 0] as int[])
        genererator3.setNonLinearSeed([0, 0, 0] as int[])
        genererator4.setNonLinearSeed([0, 0, 0] as int[])

        double d1 = genererator1.nextDouble()
        double d3 = genererator3.nextDouble()
        double d2 = genererator2.nextDouble()
        double d4 = genererator4.nextDouble()

        assertTrue d1 != d3
        assertTrue d1 == d2
        assertTrue d3 == d4

        d1 = genererator1.nextDouble()
        d3 = genererator3.nextDouble()
        d2 = genererator2.nextDouble()
        d4 = genererator4.nextDouble()

        assertTrue d1 != d3
        assertTrue d1 == d2
        assertTrue d3 == d4
    }

    public void testStringToList() {

        String str = '[[0, "1", 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],[0.875, 0.876, 0.877, 0.758]]'
        GroovyShell shell = new GroovyShell()
        def list = shell.evaluate(toGString(str))
        assertEquals list.size(), 2
        assertEquals list[0].size(), 24
        assertEquals list[0][0], 0
        assertEquals list[0][1], "1"
        assertEquals list[0][13], 13

        assertEquals list[1][0], 0.875
        assertEquals list[1][1], 0.876

    }

    private def toGString(String arg) {

        def str ="""\
          ${ getToken(arg)} 
        """
        return str
    }

    private String getToken(String arg) {
        StringBuilder sb = new StringBuilder()

        StringTokenizer st = new StringTokenizer(arg,"]", true)
        while(st.hasMoreElements()){
              sb.append(st.nextElement()+"\n")
        }
        println sb
        return sb

    }

}