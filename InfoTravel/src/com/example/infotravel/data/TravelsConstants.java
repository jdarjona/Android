/**
 * 
 */
package com.example.infotravel.data;

import android.provider.BaseColumns;

/**
 * @author Juande
 *
 */
public class TravelsConstants implements BaseColumns {

private static final String TAG="TravelColumns";

/**
 * Travel table name
 */
public static final String TRAVELS_TABLE_NAME="travels";

/**
 * The city of the travel
 *<P>Type:TEXT</P>
 */
public static final String CITY="city";

/**
 * The country of the travel
 * <P>Type:TEXT</P>
 */
public static final String COUNTRY="country";

/**
 * The year of the travel
 * <P>Type:NUMBER</P>
 */
public static final String YEAR="year";

/**
 * The note
 * <P>Type:TEXT</P>
 */

public static final String NOTE="notes";

/**
 * The image
 * <P>Type:TEXT</P>
 */

public static final String IMAGE_VIAJE="image_viaje";

}
