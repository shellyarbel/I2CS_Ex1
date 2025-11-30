
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
	 * @param x - value
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
     * input: Coordinate arrays for 2 or 3 points.
     * Converts an input set of X,Y coordinates into the algebraic representation (coefficients) of the resulting polynomial (straight line or parabola).
	 * Note: this function only works for a set of points containing up to 3 points, else returns null.
     * Uses closed-form analytical formulas (derived from solving a linear system based on the points) to find the coefficients of the polynomial.
	 * @param xx :Array of X coordinates.
	 * @param yy :Array of y coordinates.
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
        for (double i = 1;i <= degree+1;i++){
            double result1 = f(p1,i);
            double result2 = f(p2,i);
            double result = Math.abs(result2-result1);
            if (result>EPS) {
                i=degree+2;
                ans = false;}
       }
       return ans;
	}
	/**
     * input: A double array (poly) containing coefficients, ordered from constant term to highest degree.
	 * Computes a String representing the polynomial function.
     * Iterates backwards (from the highest degree down).
     * It appends the term and its power, handling signs (+ or implicit sign) between terms. The final constant term is added at the end.
     * note : I treat a NULL array as a zero array and therefore return an zero string ("0").
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
     * A String representing the polynomial function in standard algebraic notation, handling signs and omitting zero coefficients.
	 */
	public static String poly(double[] poly) {
		String ans = "";
        if(poly.length==0||poly==null) {
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
     * using the Bisection Method.
     * note:If one of the arrays is null, the operation defaults to a calculation based solely on the non-null array,
     *  effectively treating the missing polynomial's contribution as zero.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
        double[] new_p = minus(p1, p2);
        while (Math.abs(x2-x1)>EPS) {
            double midX = (x1 + x2) / 2;
            double ans1 = f(new_p, midX);
            double result = Math.abs(ans1);
            if (result <= eps)
                return midX;
            else {
                double ans2 = f(new_p, x1);
                if ((ans2 * ans1) <= 0)
                    x2 = midX;
                else x1 = midX;
            }
        }
    return (x1 + x2) / 2;
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
		double len = 0;
        double step = (x2-x1)/numberOfSegments;
        double start_x =0;
        double end_x = 0;
        double start_y =0;
        double end_y =0;
        double high =0;
        for (int i = 0; i < numberOfSegments; i++) {
            start_x = x1 + (i*step);
            end_x = x1+(i+1)*step;
            start_y = f(p,start_x);
            end_y = f(p,end_x);
            high = end_y-start_y;
            len += Math.hypot(step,high);
        }
        return len;
	}
	
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
	 * This function computes an approximation of the area between the polynomial functions within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral) and Trapezoidal Rule.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynomial functions within the [x1,x2] range.
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
        double areaS = 0;
        double step = (x2 - x1) / numberOfTrapezoid;
        for (double k = x1; k < x2; k += step) {
            double a = f(p1, k) - f(p2, k);
            double b = f(p1, k+step) - f(p2,k+step);
            if ((a * b) <= 0){
                double mid = sameValue(p1,p2,k,k+step,EPS);
                double area1 = Math.abs((a*(mid-k))/2);
                double area2 = Math.abs((b*(k+step-mid))/2);
                areaS +=area1+area2;
            }
            else {
                areaS += (Math.abs((step*(a+b))/2));
            }
        }
        return areaS;
    }

	/**
	 * This function computes the array representation of a polynomial function from a String representation.
     * Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * @param p - a String representing polynomial function.
	 * @return a double[] array representing the polynomial coefficients in standard order
     * or a ZERO array if the input string is empty or null.
	 */
	public static double[] getPolynomFromString(String p) {
		double [] ans = ZERO;
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
	 * @param p1 Coefficient array for the first polynomial.
	 * @param p2 Coefficient array for the second polynomial.
	 * @return A double[] array representing the coefficients of the resulting polynomial P1(X) + P2(X).
	 */
	public static double[] add(double[] p1, double[] p2) {
		double [] ans = ZERO;
        if (p1==null || p2==null) return ans;
        double[][] p1p2 = arrayCopy1(p1,p2);
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
	 * @param p1 Coefficient array for the first polynomial.
	 * @param p2 Coefficient array for the second polynomial.
	 * @return A double[] array representing the coefficients of the resulting polynomial P1(X) * P2(X)
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double [] ans = ZERO;
        double[][] p1p2 = arrayCopy1(p1,p2);
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
     * The method iterates through the input array, applying the power rule to each original term to find the new coefficients.
     * It calculates the new coefficient for x terms of degree i based on the original coefficient and degree,
     * storing it in the reduced-size output array, followed by removing any trailing zero coefficients.
	 * @param po - A coefficient array representing the original polynomial P(x)
	 * @return A double[] array representing the coefficients of the resulting derivative polynomial,P'(x)
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
     * the function compacts and sorts two polynomial coefficient arrays by length.
     * the function receives two coefficient arrays,p1 and p2.it first calls an auxiliary function, compact, on both arrays
     * to remove trailing zero coefficients (standardization). it then sorts the two arrays based on their effective length,
     * ensuring the shorter array is always returned as the first element (index 0) and the longer array is the second element (index 1).
     * it returns double[][] array containing the two processed arrays in sorted order.
     * @param p1 double arrays
     * @param p2 double arrays
     * @return A double array of double arrays (double[][]):
     * the pair in the order {Shorter Array,Longer Array}.
     */
    public static double[][] arrayCopy1 (double[] p1, double[] p2) {
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
     * The function compact returns a copy of the input polynomial coefficient array, removing any trailing
     * zero coefficients that do not affect the polynomial's mathematical value.
     * it first checks for edge cases (null/empty input, or last term is 0). if trailing zeros exist, it iterates backwards,
     * counting the zeros (i). it then creates a new array (com_p1) with the correct reduced length (p1.length-i)
     * and copies only the non-zero segment of the original array into it.
     * @param p1 a double array, representing polynomial coefficients
     * @return A new double array:
     * a double[] array that is a compacted copy of p1, or a ZERO array if the input was empty or entirely zeros.
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
     *The function computes the coefficient array representing the difference between two polynomial functions,P2(X) - P1(X),
     * by subtracting their corresponding coefficients.
     * it first uses an auxiliary function (arrayCopy1) to preprocess and order the arrays as P{Shorter}, P{Longer}.
     * the result array ans is initialized to the P{Longer} array. it then iterates through the length of P{Shorter},
     * subtracting its coefficients from the corresponding coefficients in P{Longer} (ans[i] -= P{Shorter}).
     * @param p1 double array representing the polynomials
     * @param p2 double array representing the polynomials
     * @return A single double array (double[]):
     *representing the coefficients of the resulting polynomial P2(X) - P1(X).
     */
    public static double[] minus(double[] p1, double[] p2) {
        if (p1==null) return p2;
        if (p2==null) return p1;
        double[][] p1p2 = arrayCopy1(p1,p2);
        double[] new_p1 = p1p2[0];
        double[] ans = p1p2[1];
        for (int i=0;i<new_p1.length;i++)
        {
            ans[i]-=new_p1[i];
        }
        return ans;
    }
}
