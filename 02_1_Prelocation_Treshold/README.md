# Percolation
Princeton Algorithm Programming Assignment 1
Write a program to estimate the value of the percolationthreshold via Monte Carlo simulation.

Percolation. Given a composite systems comprised ofrandomly distributed insulating and metallic materials: whatfraction of the materials need to be metallic so that the compositesystem is an electrical conductor? Given a porous landscape withwater on the surface (or oil below), under what conditions will thewater be able to drain through to the bottom (or the oil to gushthrough to the surface)? Scientists have defined an abstractprocess known aspercolation to model such situations.

The model. We model a percolation system using anN-by-N grid ofsites. Each site iseitheropen or blocked. A full site isan open site that can be connected to an open site in the top rowvia a chain of neighboring (left, right, up, down) open sites. Wesay the systempercolates if there is a full site in thebottom row. In other words, a system percolates if we fill all opensites connected to the top row and that process fills some opensite on the bottom row. (For the insulating/metallic materialsexample, the open sites correspond to metallic materials, so that asystem that percolates has a metallic path from top to bottom, withfull sites conducting. For the porous substance example, the opensites correspond to empty space through which water might flow, sothat a system that percolates lets water fill open sites, flowingfrom top to bottom.)
![percolates-yes](doc/percolates-yes.png) ![percolates-no](doc/percolates-no.png)
The problem. In a famous scientific problem, researchers are interested in the following question: if sites are independently set to be open with probability p (and therefore blocked with probability 1 − p), what is the probability that the system percolates? When p equals 0, the system does not percolate; when p equals 1, the system percolates. The plots below show the site vacancy probability p versus the percolation probability for 20-by-20 random grid (left) and 100-by-100 random grid (right). 
![percolation-threshold20](doc/percolation-threshold20.png)
![percolation-threshold100](doc/percolation-threshold100.png)
When n is sufficiently large, there is a threshold value p* such that when p < p* a random n-by-n grid almost never percolates, and when p > p*, a random n-by-n grid almost always percolates. No mathematical solution for determining the percolation threshold p* has yet been derived. Your task is to write a computer program to estimate p*. 

Percolation data type. To model a percolation system, create a data type Percolation with the following API:

    public class Percolation {
       public Percolation(int n)                // create n-by-n grid, with all sites blocked
       public    void open(int row, int col)    // open site (row, col) if it is not open already
       public boolean isOpen(int row, int col)  // is site (row, col) open?
       public boolean isFull(int row, int col)  // is site (row, col) full?
       public     int numberOfOpenSites()       // number of open sites
       public boolean percolates()              // does the system percolate?

       public static void main(String[] args)   // test client (optional)
    }

Corner cases.  By convention, the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site: Throw a java.lang.IllegalArgumentException if any argument to open(), isOpen(), or isFull() is outside its prescribed range. The constructor should throw a java.lang.IllegalArgumentException if n ≤ 0.

Performance requirements.  The constructor should take time proportional to n2; all methods should take constant time plus a constant number of calls to the union–find methods union(), find(), connected(), and count().

Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:

Initialize all sites to be blocked.
Repeat the following until the system percolates:
    Choose a site uniformly at random among all blocked sites.
    Open the site. 
The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold. 

For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened. 

![percolation-50](doc/percolation-50.png)
![percolation-100](doc/percolation-100.png)
![percolation-150](doc/percolation-150.png)
![percolation-204](doc/percolation-204.png)

By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate of the percolation threshold. Let xt be the fraction of open sites in computational experiment t. The sample mean provides an estimate of the percolation threshold; the sample standard deviation s measures the sharpness of the threshold.
![formula1](doc/formula1.png)
Assuming T is sufficiently large (say, at least 30), the following provides a 95% confidence interval for the percolation threshold:
![formula2](doc/formula2.png)

To perform a series of computational experiments, create a data type PercolationStats with the following API.

    public class PercolationStats {
       public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
       public double mean()                          // sample mean of percolation threshold
       public double stddev()                        // sample standard deviation of percolation threshold
       public double confidenceLo()                  // low  endpoint of 95% confidence interval
       public double confidenceHi()                  // high endpoint of 95% confidence interval

       public static void main(String[] args)        // test client (described below)
    }

The constructor should throw a java.lang.IllegalArgumentException if either n ≤ 0 or trials ≤ 0.

Also, include a main() method that takes two command-line arguments n and T, performs T independent computational experiments (discussed above) on an n-by-n grid, and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold. Use StdRandom to generate random numbers; use StdStats to compute the sample mean and sample standard deviation.

    % java-algs4 PercolationStats 200 100
    mean                    = 0.5929934999999997
    stddev                  = 0.00876990421552567
    95% confidence interval = [0.5912745987737567, 0.5947124012262428]

    % java-algs4 PercolationStats 200 100
    mean                    = 0.592877
    stddev                  = 0.009990523717073799
    95% confidence interval = [0.5909188573514536, 0.5948351426485464]


    % java-algs4 PercolationStats 2 10000
    mean                    = 0.666925
    stddev                  = 0.11776536521033558
    95% confidence interval = [0.6646167988418774, 0.6692332011581226]

    % java-algs4 PercolationStats 2 100000
    mean                    = 0.6669475
    stddev                  = 0.11775205263262094
    95% confidence interval = [0.666217665216461, 0.6676773347835391]

Analysis of running time and memory usage (optional and not graded). Implement the Percolation data type using the quick find algorithm in QuickFindUF.

This assignment was developed by Bob Sedgewick and Kevin Wayne.
Copyright © 2008. 
