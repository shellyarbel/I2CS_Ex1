Ex1: 
Polynomial Functions - Numerical Analysis -
This project implements a comprehensive set of static methods in Java for managing, manipulating, and performing numerical analysis on polynomial functions.

Polynomial RepresentationThe -
entire project operates under a single, essential data convention:
Data Type: All polynomials are represented as a double array.
Order: The coefficients are stored in ascending order of their degree, starting from the constant term (x^0).

Project API (Static Methods Summary) -
This table outlines every function implemented in the Ex1 class and its primary purpose.

1. Core Algebra & Representation:
 f(poly, x) - Computes the value of the polynomial P(x) at a specific point x.
 add(p1, p2) - Computes the coefficient array for the sum P1​(x)+P2​(x).
 minus(p1, p2) - Computes the coefficient array for the difference P2​(x)−P1​(x).
 mul(p1, p2) - Computes the coefficient array for the product P1​(x)*P2​(x) using convolution.
 derivative(po) - Computes the coefficient array for the derivative polynomial, P′(x).
 PolynomFromPoints(xx, yy) - Interpolates and calculates the coefficients for the line or parabola passing through 2 or 3 given points.
 poly(poly) - Converts a coefficient array into its standard algebraic string representation.
 getPolynomFromString(p) - Converts an algebraic string back into its coefficient array.
 equals(p1, p2) - Tests if two polynomials are mathematically identical by checking their values at N+1 points.

2. Numerical & Geometrical Analysis
 root_rec(p, x1, x2, eps) - This function recursively finds the root of the polynomial P(x) within the specified range by repeatedly halving the interval using the Bisection Method.
 sameValue(p1, p2, x1, x2, eps) - This function finds the intersection point (x) where the two polynomials P1(x) and P2(x) meet, using the Bisection Method to achieve the required tolerance eps.
 length(p, x1, x2, N) - Computes the approximate arc length of the polynomial using N straight-line segments (Pythagorean theorem).
 area(p1, p2, x1, x2, N) - Computes the approximate area between P1​ and P2​ using the Trapezoidal Rule.

<img width="761" height="756" alt="Screenshot 2025-11-30 220446" src="https://github.com/user-attachments/assets/44c5a89c-a1e8-40a1-8546-1313d49a95ed" />
The provided image  illustrates a key application of these methods: calculating the Area (shaded orange region) trapped between two polynomial curves P(Blue) and P(Green) using numerical integration techniques.
