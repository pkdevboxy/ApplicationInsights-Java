/*
 * ApplicationInsights-Java
 * Copyright (c) Microsoft Corporation
 * All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the ""Software""), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.microsoft.applicationinsights.telemetry;

import java.net.URI;
import java.util.concurrent.ConcurrentMap;

import com.microsoft.applicationinsights.internal.schemav2.PageViewData;
import com.microsoft.applicationinsights.internal.util.Sanitizer;

/**
 * Telemetry type used to track page views.
 *
 * You can send information about pages viewed by your application to Application Insights by
 * passing an instance of this class to the 'trackPageView' method of the {@link com.microsoft.applicationinsights.TelemetryClient}
 */
public final class PageViewTelemetry extends BaseTelemetry<PageViewData> {
    private PageViewData data;

    /**
     * Default Ctor
     */
    public PageViewTelemetry() {
        data = new PageViewData();
        initialize(data.getProperties());
    }

    /**
     * Initializes a new instance of the class with the specified 'pageName'
     * @param pageName The name of page to track.
     */
    public PageViewTelemetry(String pageName) {
        this();
        setName(pageName);
    }

    /**
     * Sets the name of the page view.
     * @param name The page view name.
     */
    public void setName(String name) {
        data.setName(name);
    }

    /**
     * Gets the name of the page view.
     * @return The page view name.
     */
    public String getName() {
        return data.getName();
    }

    /**
     * Gets the page view Uri.
     * @return The page view Uri.
     */
    public URI getUri() {
        URI result = Sanitizer.safeStringToUri(data.getUrl());
        if (result == null) {
            data.setUrl(null);
        }

        return result;
    }

    /**
     * Sets the page view Uri.
     * @param url The page view Uri.
     */
    public void setUrl(URI url) {
        data.setUrl(url == null ? null : url.toString());
    }

    /**
     * Gets the page view duration.
     * @return The page view duration.
     */
    public long getDuration() {
        return data.getDuration();
    }

    /**
     * Sets the page view duration.
     * @param duration The page view duration.
     */
    public void setDuration(long duration) {
        data.setDuration(duration);
    }

    /**
     * Gets a dictionary of custom defined metrics.
     * @return Custom defined metrics.
     */
    public ConcurrentMap<String, Double> getMetrics() {
        return data.getMeasurements();
    }

    @Override
    protected void additionalSanitize() {
        data.setName(Sanitizer.sanitizeName(data.getName()));
        Sanitizer.sanitizeMeasurements(this.getMetrics());
        Sanitizer.sanitizeUri(data.getUrl());
    }

    @Override
    protected PageViewData getData() {
        return data;
    }
}
