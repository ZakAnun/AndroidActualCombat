package com.zak.asm_plugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * description
 *
 * @author liminglin @Shenzhen Youzan Technology Co.Ltd
 * @date 2022/3/10.
 */
public class ViewGroupDispatchDrawMethodVisitor extends MethodVisitor {

    public ViewGroupDispatchDrawMethodVisitor(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("ViewGroup dispatchDraw 执行之前...");
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        System.out.println("ViewGroup dispatchDraw 执行之后...");
    }
}
