package antw.profiler;

import java.util.List;
import java.util.Map;

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
        Profiler.clear();
    }

    @Test
    public void testNoChildrenAndParent() throws InterruptedException {
        Map<Long, List<MethodCall>> methodCalls = Profiler.getMethodCalls();
        assertThat(methodCalls).hasSize(1);
        List<MethodCall> calls = methodCalls.values().iterator().next();
        assertThat(calls).hasSize(1);
        MethodCall methodCall = calls.get(0);
        assertThat(methodCall.getChildren()).isEmpty();
        assertThat(methodCall.getParent().getMethod()).isEqualTo(new Method(Profiler.class.getName(), "init()"));
    }

    @Test
    public void testTime() throws InterruptedException {
        Map<Long, List<MethodCall>> methodCalls = Profiler.getMethodCalls();
        assertThat(methodCalls).hasSize(1);
        List<MethodCall> calls = methodCalls.values().iterator().next();
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
        Map<Long, List<MethodCall>> methodCalls = Profiler.getMethodCalls();
        assertThat(methodCalls).hasSize(1);
        List<MethodCall> methods = methodCalls.values().iterator().next();
        assertThat(methods).hasSize(2);
        // 0=org.Foo.foo
        assertThat(methods.get(0).getChildren()).isEmpty();
        // 1=org.FooBar.foobar
        assertThat(methods.get(1).getChildren()).hasSize(1);
        MethodCall call = methods.get(1).getChildren().iterator().next();
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
        Map<Long, List<MethodCall>> methodCalls = Profiler.getMethodCalls();
        assertThat(methodCalls).hasSize(1);
        List<MethodCall> methods = methodCalls.values().iterator().next();
        assertThat(methods).hasSize(1);
    }

    @Test
    public void testCount_rootLevel() throws Exception {
        Profiler.start("org.Foo", "foo");
        Thread.sleep(100);
        Profiler.end("org.Foo", "foo");
        Map<Long, List<MethodCall>> methodCalls = Profiler.getMethodCalls();
        List<MethodCall> calls = methodCalls.values().iterator().next();
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
        Map<Long, List<MethodCall>> methodCalls = Profiler.getMethodCalls();
        assertThat(methodCalls).hasSize(1);
        List<MethodCall> methods = methodCalls.values().iterator().next();
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

        Map<Long, List<MethodCall>> methodCalls = Profiler.getMethodCalls();
        List<MethodCall> calls = methodCalls.values().iterator().next();
        assertThat(calls).hasSize(2);
        assertThat(calls.get(0).getMethod()).isEqualTo(new Method("org.Foo", "foo"));
        assertThat(calls.get(0).getChildren()).isEmpty();
        assertThat(calls.get(1).getMethod()).isEqualTo(new Method("org.FooBar", "foobar"));
        assertThat(calls.get(1).getChildren()).hasSize(1);
        assertThat(calls.get(1).getChildren().get(0).getMethod()).isEqualTo(new Method("org.FooBar", "foobar"));
    }

}
