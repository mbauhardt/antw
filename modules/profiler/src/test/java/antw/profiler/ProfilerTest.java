package antw.profiler;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ProfilerTest {

    @Before
    public void setup() throws InterruptedException {
        Profiler.start("org.Foo", "foo");
        Thread.sleep(500);
        Profiler.end("org.Foo", "foo");
    }

    @After
    public void teardown() throws InterruptedException {
        Profiler.reset();
    }

    @Test
    public void testOneChild() throws InterruptedException {
        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        assertThat(rootCall.getChildren()).isNotEmpty();
        assertThat(rootCall.getChildren()).hasSize(1);
        MethodCall childCall = rootCall.getChildren().get(0);
        assertThat(childCall.getMethod()).isEqualTo(new Method("org.Foo", "foo"));
    }

    @Test
    public void testTime() throws InterruptedException {
        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        List<MethodCall> calls = rootCall.getChildren();
        assertThat(calls).hasSize(1);
        MethodCall methodCall = calls.get(0);
        assertThat(methodCall.getCount()).isEqualTo(1);
        assertThat(methodCall.getTime()).isGreaterThanOrEqualTo(500);
        assertThat(methodCall.getTime()).isLessThan(1000);
    }

    @Test
    public void testHasChilds() throws Exception {
        Profiler.start("org.FooBar", "foobar");
        Profiler.start("org.Bar", "bar");
        Profiler.end("org.Bar", "bar");
        Profiler.end("org.FooBar", "foobar");
        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        List<MethodCall> methods = rootCall.getChildren();
        assertThat(methods).hasSize(2);
        // 0=org.Foo.foo
        assertThat(methods.get(0).getMethod().getClassName()).isEqualTo("org.Foo");
        assertThat(methods.get(0).getMethod().getMethodName()).isEqualTo("foo");
        assertThat(methods.get(0).getChildren()).isEmpty();

        // 1=org.FooBar.foobar
        assertThat(methods.get(1).getMethod().getClassName()).isEqualTo("org.FooBar");
        assertThat(methods.get(1).getMethod().getMethodName()).isEqualTo("foobar");

        assertThat(methods.get(1).getChildren()).hasSize(1);
        MethodCall call = methods.get(1).getChildren().get(0);
        assertThat(call.getChildren()).isEmpty();
        assertThat(call.getMethod().getClassName()).isEqualTo("org.Bar");
        assertThat(call.getMethod().getMethodName()).isEqualTo("bar");
    }

    @Test
    public void testDisable() throws Exception {
        Profiler.disable();
        Profiler.start("org.FooBar", "foobar");
        Profiler.start("org.Bar", "bar");
        Profiler.end("org.Bar", "bar");
        Profiler.end("org.FooBar", "foobar");
        Profiler.enable();
        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        List<MethodCall> methods = rootCall.getChildren();
        assertThat(methods).hasSize(1);
    }

    @Test
    public void testCount_rootLevel() throws Exception {
        Profiler.start("org.Foo", "foo");
        Thread.sleep(100);
        Profiler.end("org.Foo", "foo");
        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        List<MethodCall> calls = rootCall.getChildren();
        assertThat(calls).hasSize(1);
        assertThat(calls.get(0).getCount()).isEqualTo(2);
        assertThat(calls.get(0).getTime()).isGreaterThanOrEqualTo(600);
    }

    @Test
    public void testCount_childLevel() throws Exception {
        Profiler.start("org.FooBar", "foobar");
        Profiler.start("org.Bar", "bar");
        Profiler.end("org.Bar", "bar");
        Profiler.start("org.Bar", "bar");
        Profiler.end("org.Bar", "bar");
        Profiler.end("org.FooBar", "foobar");
        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        List<MethodCall> methods = rootCall.getChildren();
        assertThat(methods).hasSize(2);

        assertThat(methods.get(0).getMethod()).isEqualTo(new Method("org.Foo", "foo"));
        assertThat(methods.get(0).getChildren()).isEmpty();
        assertThat(methods.get(1).getMethod()).isEqualTo(new Method("org.FooBar", "foobar"));
        assertThat(methods.get(1).getChildren()).hasSize(1);
        MethodCall bar = methods.get(1).getChildren().iterator().next();
        assertThat(bar.getMethod()).isEqualTo(new Method("org.Bar", "bar"));
        assertThat(bar.getCount()).isEqualTo(2);
    }

    @Test
    public void testRecursion() throws Exception {
        Profiler.start("org.FooBar", "foobar");
        Profiler.start("org.FooBar", "foobar");
        Profiler.end("org.FooBar", "foobar");
        Profiler.end("org.FooBar", "foobar");

        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        List<MethodCall> calls = rootCall.getChildren();
        assertThat(calls).hasSize(2);
        assertThat(calls.get(0).getMethod()).isEqualTo(new Method("org.Foo", "foo"));
        assertThat(calls.get(0).getChildren()).isEmpty();
        assertThat(calls.get(1).getMethod()).isEqualTo(new Method("org.FooBar", "foobar"));
        assertThat(calls.get(1).getChildren()).hasSize(1);
        assertThat(calls.get(1).getChildren().get(0).getMethod()).isEqualTo(new Method("org.FooBar", "foobar"));
    }

    @Test
    public void testReset() throws Exception {
        Profiler.reset();
        Profiler.start("org.Bar", "bar");
        Profiler.end("org.Bar", "bar");
        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        assertThat(rootCall.getChildren()).hasSize(1);
        assertThat(rootCall.getChildren().get(0).getMethod()).isEqualTo(new Method("org.Bar", "bar"));
        Profiler.reset();
        Profiler.start("org.FooBar", "foobar");
        Profiler.end("org.FooBar", "foobar");
        rootCall = Profiler.getRootMethodFromCurrentThread();
        assertThat(rootCall.getChildren()).hasSize(1);
        assertThat(rootCall.getChildren().get(0).getMethod()).isEqualTo(new Method("org.FooBar", "foobar"));

    }

}
