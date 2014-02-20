
package cz.cvut.fel.adaptiverestfulapi.meta.reflection;


/**
 * Group of three.
 */
public class Triplet<A, B, C> {

    public A a;
    public B b;
    public C c;

    public Triplet(A a, B b, C c) {
        this.a = a; this.b = b; this.c = c;
    }

    @Override
    public String toString() {
        return "<" + this.a + ", " + this.b + ", " + this.c + ">";
    }

}
