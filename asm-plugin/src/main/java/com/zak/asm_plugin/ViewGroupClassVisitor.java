package com.zak.asm_plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * description
 *
 * @author liminglin @Shenzhen Youzan Technology Co.Ltd
 * @date 2022/3/10.
 */
public class ViewGroupClassVisitor extends ClassVisitor implements Opcodes {

    private String mClassName = "";

    public ViewGroupClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("ViewGroupClassVisitor: method: " + name);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if ("android/view/ViewGroup".equals(this.mClassName)) {
            if ("dispatchDraw".equals(name)) {
                System.out.println("ViewGroupClassVisitor: change method ==== " + name);
                return new ViewGroupDispatchDrawMethodVisitor(mv);
            }
        }
        return mv;
    }
}
