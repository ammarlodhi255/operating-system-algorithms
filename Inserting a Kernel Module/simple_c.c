/* This code is used to create a kernal module, in order to compile this file
   we need to create a "make file". This along with the make_file must be kept in the same
   directory
*/

#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>

int simple_init(void) {
	printk(KERN_INFO "Ammar's Module Loading..\n");
	return 0;
}

void simple_exit(void) {
        printk(KERN_INFO "Ammar's Module Being removed..\n");
} 

module_init(simple_init);
module_exit(simple_exit);

MODULE_LICENSE("GPL");
MODULE_DESCRIPTION("Simple Module");
MODULE_AUTHOR("SGG");
