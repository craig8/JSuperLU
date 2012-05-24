/*! @file Dlu_dutil.java
 * \brief Matrix utility functions
 *
 * <pre>
 * -- SuperLU routine (version 3.1) --
 * Univ. of California Berkeley, Xerox Palo Alto Research Center,
 * and Lawrence Berkeley National Lab.
 * August 1, 2008
 *
 * Copyright (c) 1994 by Xerox Corporation.  All rights reserved.
 *
 * THIS MATERIAL IS PROVIDED AS IS, WITH ABSOLUTELY NO WARRANTY
 * EXPRESSED OR IMPLIED.  ANY USE IS AT YOUR OWN RISK.
 *
 * Permission is hereby granted to use or copy this program for any
 * purpose, provided the above notices are retained on all copies.
 * Permission to modify the code and to distribute modified code is
 * granted, provided the above notices are retained, and a notice that
 * the code was modified is included with the above copyright notice.
 * </pre>
 */
package lu.jsuper;

import lu.jsuper.Dlu_slu_ddefs.GlobalLU_t;
import lu.jsuper.Dlu_supermatrix.DNformat;
import lu.jsuper.Dlu_supermatrix.Dtype_t;
import lu.jsuper.Dlu_supermatrix.Mtype_t;
import lu.jsuper.Dlu_supermatrix.SCformat;
import lu.jsuper.Dlu_supermatrix.Stype_t;
import lu.jsuper.Dlu_supermatrix.SuperMatrix;
import lu.jsuper.Dlu_supermatrix.NCformat;

public class Dlu_dutil {

	public static SuperMatrix dCreate_CompCol_Matrix(int m, int n, int nnz,
		       double nzval[], int rowind[], int colptr[],
		       Stype_t stype, Dtype_t dtype, Mtype_t mtype) {
		NCformat Astore;

		SuperMatrix A = new SuperMatrix();

	    A.Stype = stype;
	    A.Dtype = dtype;
	    A.Mtype = mtype;
	    A.nrow = m;
	    A.ncol = n;
	    A.Store = new NCformat();
	    Astore = (NCformat) A.Store;
	    Astore.nnz = nnz;
	    Astore.nzval = nzval;
	    Astore.rowind = rowind;
	    Astore.colptr = colptr;

	    return A;
	}

	public static SuperMatrix dCreate_Dense_Matrix(int m, int n, double x[],
			int ldx, Stype_t stype, Dtype_t dtype, Mtype_t mtype) {
	    DNformat Xstore;

	    SuperMatrix X = new SuperMatrix();

	    X.Stype = stype;
	    X.Dtype = dtype;
	    X.Mtype = mtype;
	    X.nrow = m;
	    X.ncol = n;
	    X.Store = new DNformat();
	    Xstore = (DNformat) X.Store;
	    Xstore.lda = ldx;
	    Xstore.nzval = x;

	    return X;
	}

	public static SuperMatrix dCreate_SuperNode_Matrix(int m, int n, int nnz,
			double nzval[], int nzval_colptr[], int rowind[],
			int rowind_colptr[], int col_to_sup[], int sup_to_col[],
			Stype_t stype, Dtype_t dtype, Mtype_t mtype) {
	    SCformat Lstore;

	    SuperMatrix L = new SuperMatrix();

	    L.Stype = stype;
	    L.Dtype = dtype;
	    L.Mtype = mtype;
	    L.nrow = m;
	    L.ncol = n;
	    L.Store = new SCformat();
	    Lstore = (SCformat) L.Store;
	    Lstore.nnz = nnz;
	    Lstore.nsuper = col_to_sup[n];
	    Lstore.nzval = nzval;
	    Lstore.nzval_colptr = nzval_colptr;
	    Lstore.rowind = rowind;
	    Lstore.rowind_colptr = rowind_colptr;
	    Lstore.col_to_sup = col_to_sup;
	    Lstore.sup_to_col = sup_to_col;

	    return L;
	}

	/**! \brief Diagnostic print of column "jcol" in the U/L factor.
	 */
	public static void
	dprint_lu_col(String msg, int jcol, int pivrow, int xprune[], GlobalLU_t Glu)
	{
	    int     i, k, fsupc;
	    int     xsup[], supno[];
	    int     xlsub[], lsub[];
	    double  lusup[];
	    int     xlusup[];
	    double  ucol[];
	    int     usub[], xusub[];

	    xsup    = Glu.xsup;
	    supno   = Glu.supno;
	    lsub    = Glu.lsub;
	    xlsub   = Glu.xlsub;
	    lusup   = Glu.lusup;
	    xlusup  = Glu.xlusup;
	    ucol    = Glu.ucol;
	    usub    = Glu.usub;
	    xusub   = Glu.xusub;

	    System.out.printf("%s", msg);
	    System.out.printf("col %d: pivrow %d, supno %d, xprune %d\n",
		   jcol, pivrow, supno[jcol], xprune[jcol]);

	    System.out.printf("\tU-col:\n");
	    for (i = xusub[jcol]; i < xusub[jcol+1]; i++)
	    	System.out.printf("\t%d%10.4f\n", usub[i], ucol[i]);
	    System.out.printf("\tL-col in rectangular snode:\n");
	    fsupc = xsup[supno[jcol]];	/* first col of the snode */
	    i = xlsub[fsupc];
	    k = xlusup[jcol];
	    while ( i < xlsub[fsupc+1] && k < xlusup[jcol+1] ) {
	    	System.out.printf("\t%d\t%10.4f\n", lsub[i], lusup[k]);
	    	i++; k++;
	    }
	    System.out.flush();
	}

	/**! \brief Fills a double precision array with a given value.
	 */
	public static void dfill(double a[], int alen, double dval)
	{
	    int i;
	    for (i = 0; i < alen; i++) a[i] = dval;
	}

}
