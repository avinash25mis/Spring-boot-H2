package com.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author avinash.a.mishra
 */
public interface AppLogger {

    public static Logger APPLICATION_LOGGER = LoggerFactory.getLogger("application_logger");

    public static Logger RECHARGE_SCHEDULER = LoggerFactory.getLogger("rechargeScheduler");

    public static Logger PAYMENT_SCHEDULER = LoggerFactory.getLogger("paymentScheduler");

    public static Logger NOTIFICATION_LOGGER = LoggerFactory.getLogger("notificationLogger");


    public static Logger LOW_BALANCE_FEED = LoggerFactory.getLogger("low_balance_feed");

    public static Logger RECHARGE_FEED = LoggerFactory.getLogger("recharge_feed");

    public static Logger CHANGE_MSN = LoggerFactory.getLogger("changeMsn");

    public static Logger DELETE_MSN = LoggerFactory.getLogger("deleteMsn");


    public static Logger EMAGINE_OFFER_FEED = LoggerFactory.getLogger("emagine_offer_feed");

    public static Logger AR_MODIFICATION_FEED = LoggerFactory.getLogger("ar_feed");

    public static Logger PENDING_OFFER_SCHEDULER = LoggerFactory.getLogger("PendingOfferScheduler");


}
