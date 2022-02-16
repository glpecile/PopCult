import {AnimatePresence, motion} from "framer-motion";

const FadeIn = ({children, isActive}) => {
    return (
        <AnimatePresence>
            {
                isActive &&
                (
                    <motion.div
                        className="z-50 absolute"
                        key="hint"
                        initial={{opacity: 0, y: 100}}
                        exit={{opacity: 0, y: 100}}
                        animate={{opacity: 1, y: 0}}
                    >
                        {children}
                    </motion.div>
                )
            }
        </AnimatePresence>
    );
}
export default FadeIn;