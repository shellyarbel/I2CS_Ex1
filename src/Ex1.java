import java.util.Arrays;

/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 *
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
public class Ex1 {
	/** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynomial function is represented as an array with a single (0) entry. */
	public static final double[] ZERO = {0};
	/**
	 * Computes the f(x) value of the polynomial function at x.
	 * @param poly - polynomial function
	 * @param x
	 * @return f(x) - the polynomial function value at x.
	 */
	public static double f(double[] poly, double x) {
		double ans = 0;
		for(int i=0;i<poly.length;i++) {
			double c = Math.pow(x, i);
			ans += c*poly[i];
		}
		return ans;
	}
	/** Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0.
	 * This function should be implemented recursively.
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps) {
		double f1 = f(p,x1);
		double x12 = (x1+x2)/2;
		double f12 = f(p,x12);
		if (Math.abs(f12)<eps) {return x12;}
		if(f12*f1<=0) {return root_rec(p, x1, x12, eps);}
		else {return root_rec(p, x12, x2, eps);}
	}
	/**
	 * This function computes a polynomial representation from a set of 2D points on the polynom.
	 * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
	 * Note: this function only works for a set of points containing up to 3 points, else returns null.
	 * @param xx
	 * @param yy
	 * @return an array of doubles representing the coefficients of the polynom.
	 */
public static double[] PolynomFromPoints(double[] xx, double[] yy) {
    double [] ans = null;
    int lx = xx.length;
    int ly = yy.length;
    if(xx!=null && yy!=null && lx==ly && lx>1 && lx<4) {
        if (lx == 3) {
            double denom = (xx[0] - xx[1]) * (xx[0] - xx[2]) * (xx[1] - xx[2]);
            if (Math.abs(denom)<=EPS) return ans;
            double A = (xx[2] * (yy[1] - yy[0]) + xx[1] * (yy[0] - yy[2]) + xx[0] * (yy[2] - yy[1])) / denom;
            double B = (xx[2] * xx[2] * (yy[0] - yy[1]) + xx[1] * xx[1] * (yy[2] - yy[0]) + xx[0] * xx[0] * (yy[1] - yy[2])) / denom;
            double C = (xx[1] * xx[2] * (xx[1] - xx[2]) * yy[0] + xx[2] * xx[0] * (xx[2] - xx[0]) * yy[1] + xx[0] * xx[1] * (xx[0] - xx[1]) * yy[2]) / denom;
            ans=new double[]{A,B,C};
        }
        else if (lx == 2) {
            if (Math.abs(xx[0]-xx[1])<=EPS) return ans;
            double A = (yy[0]-yy[1])/(xx[0]-xx[1]);
            double B = yy[0]-(A*xx[0]);
            ans=new double[]{A,B};
        }
    }
    return ans;
}
	/** Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
	 * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
	 * @param p1 first polynomial function
	 * @param p2 second polynomial function
	 * @return true iff p1 represents the same polynomial function as p2.
	 */
	public static boolean equals(double[] p1, double[] p2) {
		boolean ans = true;
        if (p1==null)
        {
            if (p2==null) {return ans;}
            else {
                ans = false;
                return ans;}
        }
        double degree = Math.max(p1.length,p2.length);
        for (double i = 1;i <= degree+1;i++)
        {
            double result1 = f(p1,i);
            double result2 = f(p2,i);
            if (result2 > result1)
            {
                double result = result2-result1;
                if (result>EPS) {
                    i=degree+2;
                    ans = false;}
            }
            else {
                double result = result1-result2;
                if (result>EPS) {
                i=degree+2;
                ans = false;}}
        }
        return ans;
	}

	/** 
	 * Computes a String representing the polynomial function.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
	 */
	public static String poly(double[] poly) {
		String ans = "";
		if(poly.length==0) {
            ans="0";
            return ans;}
            for (int i=poly.length-1 ; i > 0; i--) {
                if (poly[i]!=0){
                    if (i==1){ans += poly[i]+"x"+" ";}
                    else{
                    ans += poly[i]+"x"+"^"+(i)+" ";}}
                if (poly[i-1]>0) ans += "+";
            }
        if (poly[0]!=0) ans += poly[0];
		return ans;
	}
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double ans = -1;
            while (ans==-1) {
                double midX = (x1 + x2) / 2;
                double[] new_p = minus(p1, p2);
                double ans1 = f(new_p, midX);
                double result = Math.abs(ans1);
                if (result <= eps)
                    ans = midX;
                else {
                    double ans2 = f(new_p, x1);
                    if ((ans2 * ans1) < 0)
                        x2 = midX;
                    else x1 = midX;
                }
            }
		return ans;
	}
	/**
	 * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
	 * This function computes an approximation of the length of the function between f(x1) and f(x2) 
	 * using n inner sample points and computing the segment-path between them.
	 * assuming x1 < x2. 
	 * This function should be implemented iteratively (none recursive).
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
	 */
	public static double length(double[] p, double x1, double x2, int numberOfSegments) {
		double ans = x1;
        /** add you code below

         /////////////////// */
		return ans;
	}
	
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
	 * This function computes an approximation of the area between the polynomial functions within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynomial functions within the [x1,x2] range.
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
		double ans = 0;
        /** add you code below

         /////////////////// */
		return ans;
	}
	/**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynomial function.
	 * @return
	 */
	public static double[] getPolynomFromString(String p) {
		double [] ans = ZERO;//  -1.0x^2 +3.0x +2.0
        if (p==null||p.isEmpty())
            return ans;
        p = p.toLowerCase();
        String [] new_p = p.trim().split("\\s+");
        int maxDegree = 0;
        for (int i=0;i<new_p.length;i++){
            String term = new_p[i];
            int d_location = term.indexOf("^");
            if (d_location != -1) {
                int degree = Integer.parseInt(term.substring(d_location+1));
                maxDegree = Math.max(maxDegree,degree);}
           else if (term.contains("x"))
               maxDegree = Math.max(maxDegree,1);
        }
        ans = new double[maxDegree+1];
        for (int i=0;i<new_p.length;i++) {
                String term = new_p[i];
                int x_location = term.indexOf("x");
                String new_term = (x_location == -1) ? term : term.substring(0, x_location);
                if (new_term.startsWith("+"))
                    new_term = new_term.substring(1);
                if (x_location==-1){
                    ans[0] = Double.parseDouble(new_term);}
                else{
                    int d_location = term.indexOf("^");
                    if (d_location==-1) {
                        ans[1] = Double.parseDouble(new_term);}
                    else{
                        int degree = Integer.parseInt(term.substring(d_location+1));
                        ans[degree] = Double.parseDouble(new_term);}
                }
        }
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2):
     * go over length of short array, add numbers from short array to the same place in long array.
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] add(double[] p1, double[] p2) {
		double [] ans = ZERO;
        if (p1==null || p2==null) return ans;
        double[][] p1p2 = arrayCopy(p1,p2);
        double[] new_p1 = p1p2[0];
        ans = p1p2[1];
        for (int i=0;i<new_p1.length;i++)
        {
            ans[i]+=new_p1[i];
        }
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double [] ans = ZERO;
        double[][] p1p2 = arrayCopy(p1,p2);
        double[] new_p1 = p1p2[0];
        double[] new_p2 = p1p2[1];
        if (new_p1==ZERO || new_p2==ZERO) return ZERO;
        ans = new double[(new_p1.length+new_p2.length)-1];
        for (int i=0;i<new_p1.length;i++){
            for (int j=0;j<new_p2.length;j++) {
                double t = ans [i+j];
                ans [i+j] = new_p1[i]*new_p2[j] +t;
            }
        }
		return ans;
	}
	/**
	 * This function computes the derivative of the p0 polynomial function.
	 * @param po\
	 * @return
	 */
	public static double[] derivative (double[] po) {
		double [] ans = ZERO;
        if (po==null || po.length<=1) return ans;
        ans = new double[po.length-1];
            for (int i=0; i < ans.length; i++)
            {
                ans [i] = (po[i+1]*(i+1));
            }
        if (ans[ans.length-1]==0){ans = compact(ans);

        }
		return ans;
	}

    /**
     * input: Two arrays of doubles,p1 and p2,representing polynomial coefficients.
     * Action: Standardizes and sorts the two input arrays based on their effective length.
     * Method:
     *  1.Compacting: Calls an external compact() function on both arrays (removes insignificant zeros).
     *  2.Ordering: Compares the final length of the two arrays.
     *  3.Swapping: If the first array (new_p1) is longer than the second (new_p2), it swaps their positions.
     * @param p1
     * @param p2
     * @return A double array of double arrays (double[][]):
     * the pair in the order {Shorter Array,Longer Array}.
     */
    public static double[][] arrayCopy (double[] p1, double[] p2) {
        double[] new_p1 = compact(p1);
        double[] new_p2 = compact(p2);
        if (new_p1.length <= new_p2.length) {return new double[][]{new_p1, new_p2};
        }
        else {
            double[] t = new_p1;
            new_p1 = new_p2;
            new_p2 = t;
            return new double[][]{new_p1, new_p2};
        }
    }

    /**
     * input: p1[]
     * Action: Removes trailing zeros from the input array:
     *  1.his process compacts the array to its shortest necessary length
     *  2.eliminating zero coefficients that do not affect the polynomial's degree
     * Method:
     *  1.Edge Case Check: Handles null or empty input arrays (returns a pre-defined ZERO array, which presumably is)
     *  2.No Trailing Zeros: If the last element is non-zero, it returns a direct copy of the array (no compression needed).
     *  3.Trailing Zeros Loop: It uses a while loop starting from the end of the array to count the number of consecutive zeros (i).
     *  4.Full Zero Array: If the loop finds that the entire array consists of zeros, it returns ZERO.
     *  5.Copying: It creates a new array (com_p1) with the calculated shorter length (p1.length-i)
     *    and copies the non-zero segment of the original array into it.
     * @param p1
     * @return A new double array:
     * that is a compacted copy of the original array p1, with all trailing zeros removed.
     * Special return:ZERO if the input array was empty, null, or consisted only of zeros
     */
    public static double[] compact (double[] p1) {
        if (p1.length==0 || p1==null ) return ZERO;
        if (p1[p1.length-1]!=0){
            double[] com_p1 = new double[p1.length];
            System.arraycopy(p1, 0, com_p1, 0, com_p1.length);
            return com_p1;}
        int i =1;
        while (p1[p1.length-i]==0){
            i++;
            if (p1.length-(i)<0){break;}
        }
        i-=1;
        if (p1.length-i==0) return ZERO;
        double[] com_p1 = new double[p1.length-i];
        System.arraycopy(p1, 0, com_p1, 0, com_p1.length);
        return com_p1;
    }

    /**
     *input: Two double arrays p1 and p2, representing the coefficients of two polynomials.
     *Action: Computes the difference between the two input polynomials {P2-P1} by subtracting the coefficients element wise.
     *Method:
     *  1.Edge Case Check:
     *   If either input is null, it returns a pre-defined ZERO array.
     *  2.Standardization:
     *   It calls the auxiliary function arrayCopy(p1, p2).
     *    This function returns the two compacted polynomials in a standardized order:
     *      +new_p1 becomes the shorter (or equal length) polynomial.
     *      +ans=p1p2[1] becomes the longer polynomial
     *  3.Subtraction Loop:
     *   +It iterates through the length of the shorter polynomial (new_p1.length).
     *   +In the loop, it subtracts the coefficients of the shorter polynomial from the corresponding coefficients
     *     of the longer polynomial (ans).
     * @param p1
     * @param p2
     * @return A single double array (double[]):
     * representing the coefficients of the resulting polynomial PLonger-PShorter.
     */
    public static double[] minus(double[] p1, double[] p2) {
        double [] ans = ZERO;
        if (p1==null || p2==null) return ans;
        double[][] p1p2 = arrayCopy(p1,p2);
        double[] new_p1 = p1p2[0];
        ans = p1p2[1];
        for (int i=0;i<new_p1.length;i++)
        {
            ans[i]-=new_p1[i];
        }
        return ans;
    }
}
