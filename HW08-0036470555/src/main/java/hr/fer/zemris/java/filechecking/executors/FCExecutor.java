package hr.fer.zemris.java.filechecking.executors;

import hr.fer.zemris.java.filechecking.FCFileVerifier.FCFileVerifier;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Executor class executes parsed program by getting program node. It has
 * reference to private FCFileVerifier and when error occurs error message is
 * delegated to FCFileVerifier.
 * 
 * @author Ivan Pavić
 * 
 */
public class FCExecutor {
        /**
         * Reference to program node.
         */
        private final ProgramNode programNode;
        /**
         * Reference to verifier.
         */
        private final FCFileVerifier verifier;
        /**
         * If true, execution stops.
         */
        private boolean stop = false;
        /**
         * Variables in execution.
         */
        private final Map<String, String> variables = new HashMap<>();

        /**
         * Constructor accepts programNode and verifier references as arguments.
         * 
         * @param programNode
         *                given programNode
         * @param verifier
         *                given {@link FCFileVerifier}
         */
        public FCExecutor(final ProgramNode programNode,
                        final FCFileVerifier verifier) {
                this.programNode = programNode;
                this.verifier = verifier;
        }

        /**
         * Method starts execution of statements by executing programNode.
         * 
         * @param programNode
         */
        public final void execute(final ProgramNode programNode) {
                programNode.execute(this);
        }

        /**
         * Getter for variable of given name.
         * 
         * @param name
         *                given name
         * @return value of variable
         */
        public final String getVariable(final String name) {
                return variables.get(name);
        }

        /**
         * Adds variable to collection of variables. Two arguments are needed.
         * Variable name and variable value.
         * 
         * @param name
         *                given variable name
         * @param value
         *                variable value
         */
        public final void addVariable(final String name, final String value) {
                variables.put(name, value);
        }

        /**
         * Getter for programNode.
         * 
         * @return programNode
         */
        public final ProgramNode getProgramNode() {
                return programNode;
        }

        /**
         * Getter for private verifier.
         * 
         * @return the verifier
         */
        public final FCFileVerifier getVerifier() {
                return verifier;
        }

        /**
         * Getter for stop flag.
         * 
         * @return the stop
         */
        public final boolean isStop() {
                return stop;
        }

        /**
         * Setter for stop flag.
         * 
         * @param stop
         *                the stop to set
         */
        public final void setStop(final boolean stop) {
                this.stop = stop;
        }

}
