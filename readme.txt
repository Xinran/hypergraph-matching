ReadMe
To run the tests, e.g., to query a sub-hypergraph on a small hypergraph, open testSmall.java and follow the main function. 

In this package, there are 17 classes. To keep record of previous versions of code, some of them are not used for the latest version of the paper. 

The obsolete classes: 
createGraph.java: generate the hypergraph for the real world paper-author dataset.
determin.java: includes version 1 of indexing generating and verification methods.
newDeter.java: include version 2 of indexing generating and verification methods.
newDetermin.java: include version 3 of indexing generating and verification methods.
secondlevelindex.java: include indexing generating and verification methods for second level indexing.
test/testKnown/testKnownCorrectness. java: test class for old version methods on random data.

The classes currently used:
createindexwithoutDup.java: latest version of indexing generating and verification methods.
genQuery: includes methods for generating a random hypergraph dataset/query.
indexTime: test class for testing the indexing time.
newCreateGraph.java: latest version of generateingthe hypergraph for the real world paper-author dataset.
testSmall. java: test class for latest version methods on random data.

Other classes for future related research:
shortest.java, similarity.java, temp.java.
