/* $Id: hashtab.h,v 1.2 2008-07-02 10:08:23 ericsson Exp $ */
#ifndef MUCK_HASHTAB_H
#define MUCK_HASHTAB_H

//#ifdef __cplusplus
//extern "C" {
//#endif

/*!     \file hashtab.h
\brief Hashtable header file.

This file contains all hashtable typedefs and functions.
 */

/*!     \typedef int (*compare_function)(const void *, const void *)
\brief Compare function typedef

Comparison function used to check if two hash table entries are the same each other.

\param *compare_function Comparison function called.
\param const void * First hash table entry.
\param const void * Second hash table entry to be checked.
\return Should return 0 if the same, non-zero otherwise.
 */
typedef int (*compare_function)(const void *, const void *);

/*!     \typedef unsigned long (*key_function)(const void *)
\brief Key generating function.

\param *key_function Comparison function called.
\param const void * New hash table entry.
\return Return the key for a hash table entry.
 */
typedef unsigned long (*key_function)(const void *);

/*!     \typedef void (*delete_function)(void *)
\brief Delete function definition.

Function to be called when a hash entry is to be deleted.

\param *delete_function Specific Delete function.
\param const void * Hash table entry to be deleted.
 */
typedef void (*delete_function)(void *);

/*!     \struct hash_tab
\brief Hash table type definition.
 */
struct hash_tab;
//typedef struct hash_tab hash_tab;

/*!     \fn extern void free_hash_table(hash_tab *table)
\brief Removing a given hash table.

The generic delete function simply free's it's argument.

\param *table Hash table to be deleted.
 */

class HashTableUtil
{
	public:
		static void free_hash_table(hash_tab *table);

	/*!     \fn extern int compare_generic_hash(const void *entry1, const void *entry2)
		\brief This generic function requires a string as key.

		It verifies whether two entries are equals.

		\param *entry1 First hash table entry to be compared.
		\param *entry2 Second hash table entry to be compared.
		\return Should return 0 if the same, non-zero otherwise.
	 */
		static int compare_generic_hash(const void *entry1, const void *entry2);

	/*!     \fn extern unsigned long make_key_generic_hash(const void *entry)
		\brief This function requires a generic struct to establish a key value.

		\param *entry forthcoming hash table entry.
		\return Return a bit sequence that means the key value's entry.
	 */
		static unsigned long make_key_generic_hash(const void *entry);

	/*!     \fn extern void delete_generic_hash(void *entry)
		\brief This function deletes a given hash table entry.

		\param *entry Hash table entry.
	 */
		static void delete_generic_hash(void *entry);

	/*!     \fn extern void *find_hash_entry(hash_tab *table, const void *match)
		\brief This function searches the existence of a struct into a hash table.

		\param *table Hash table which must be searched a generic structure.
		\param *match A given generic structure to be searched.
		\return Return the hash table entry if it exists, NULL pointer otherwise.
	 */
		static void *find_hash_entry(hash_tab *table, const void *match);

	/*!     \fn extern int add_hash_entry(hash_tab *table, const void *data)
		\brief Add a generic structure to a hash table.

		\param *table Hash table which must be added a generic structure.
		\param *data A given generic structure to be added.
		\return Should return 0 if the entry is correct added, non-zero otherwise.
	 */
		static int add_hash_entry(hash_tab *table, const void *data);

	/*!     \fn extern void clear_hash_entry(hash_tab *table, const void *data)
		\brief Find and clear a specific entry from a hash table.

		\param *table Hash table which will be searched a entry to be deleted.
		\param *data A given generic structure to be deleted.
	 */
		static void clear_hash_entry(hash_tab *table, const void *data);

	/*!     \fn extern void clear_hash_table(hash_tab *table)
		\brief Clear out a given hash table.

		\param *table Hash table to be cleared out.
	 */
		static void clear_hash_table(hash_tab *table);

	/*!     \fn extern hash_tab *init_hash_table(const char *id, compare_function cmpfn, key_function keyfn, delete_function delfn
		, unsigned long size)
		\brief Start off a hash table.

		\param *id ''Name'' of hashtable, for stat display.
		\param cmpfn Function to compare two entries.
		\param keyfn Function to generate a key for an entry.
		\param delfn Function called when freeing an entry.
		\param size Size of the hashtable.
		\return Return a point to a new hash table.
	 */
		static hash_tab *init_hash_table(const char *id, compare_function cmpfn, key_function keyfn, delete_function delfn, unsigned long size);

	/*!     \fn extern void init_hash_walk(hash_tab *table)
		\brief Enable a hash table to go walking through.

		\param *table Hash table to be dealt out.
	 */
		static void init_hash_walk(hash_tab *table);

	/*!     \fn extern void *next_hash_walk(hash_tab *table)
		\brief This function allows to get hash table entries one by one.

		\param *table Hash table to go walking.
		\return Return the next hash table entry.
	 */
		static void *next_hash_walk(hash_tab *table);

	/*!     \fn extern void dump_hashtab_stats(hash_tab *table)
		\brief Dump a hash table.

		\param *table Hash table to be dumped on.
	 */
		static void dump_hashtab_stats(hash_tab *table);

	/*!     \fn extern long total_hash_entries(void)
		\brief #### MAKE_ALLOCATOR can't pluralize well .

		\return Return total_hash_entrys value.
	 */
		static long total_hash_entries(void);

	/*!     \fn extern long num_hash_entries(hash_tab *table)
		\brief Inform the total number of entries in a hash table.

		\param *table Hash table to be counted.
		\return Return the total number of entries..
	 */
		static long num_hash_entries(hash_tab *table);

	/*!     \fn extern void init_hash_entries(const long count)
		\brief #### MAKE_ALLOCATOR can't pluralize well. Call generate_hash_entrys(count) function.

		\param count number of elements.
	 */
		static void init_hash_entries(const long count);

};

#endif /* MUCK_HASHTAB_H */
